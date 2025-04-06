package com.av.pixel.service.impl;

import com.av.pixel.dao.User;
import com.av.pixel.dao.UserCredit;
import com.av.pixel.dto.UserDTO;
import com.av.pixel.dto.UserTokenDTO;
import com.av.pixel.helper.UserHelper;
import com.av.pixel.mapper.UserMapper;
import com.av.pixel.repository.UserRepository;
import com.av.pixel.request.SignInRequest;
import com.av.pixel.request.SignUpRequest;
import com.av.pixel.response.SignInResponse;
import com.av.pixel.response.SignUpResponse;
import com.av.pixel.service.UserCreditService;
import com.av.pixel.service.UserService;
import com.av.pixel.service.UserTokenService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserHelper userHelper;

    UserCreditService userCreditService;
    UserTokenService userTokenService;

    @Override
    public User createUser (UserDTO userDTO) {
        userHelper.validateNewUserRequest(userDTO);

        if (StringUtils.isEmpty(userDTO.getPassword())) {
            userDTO.setPassword(userHelper.getEncodedPassword());
        } else {
            userDTO.setPassword(userHelper.encodePassword(userDTO.getPassword()));
        }

        User user = UserMapper.INSTANCE.toEntity(userDTO);
        user = userRepository.save(user);

        return user;
    }

    @Override
    public SignUpResponse signUp (SignUpRequest signUpRequest) {
        UserDTO userDTO = UserMapper.INSTANCE.toDTO(signUpRequest);
        User user = createUser(userDTO);
        UserCredit userCredit = userCreditService.createNewUserCredit(user);
        UserTokenDTO userTokenDTO = userTokenService.registerToken(user.getCode());
        return UserMapper.INSTANCE.toResponse(userDTO);
    }

    @Override
    public SignInResponse signIn (SignInRequest signInRequest) {
        UserDTO userDTO = UserMapper.INSTANCE.toDTO(signInRequest);
        User user = userRepository.findByEmail(userDTO.getEmail());

        if (Objects.isNull(user)) {
            SignUpResponse signUpResponse = signUp(UserMapper.INSTANCE.toSignUpRequest(signInRequest));
            return UserMapper.INSTANCE.toSignInResponse(signUpResponse);
        }

        UserTokenDTO userTokenDTO = userTokenService.registerToken(user.getCode(), signInRequest.getAuthToken());

        return UserMapper.INSTANCE.toSignInResponse(UserMapper.INSTANCE.toDTO(user));
    }
}
