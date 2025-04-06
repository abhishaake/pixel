package com.av.pixel.service.impl;

import com.av.pixel.dao.User;
import com.av.pixel.dao.UserCredit;
import com.av.pixel.dto.UserCreditDTO;
import com.av.pixel.dto.UserDTO;
import com.av.pixel.dto.UserTokenDTO;
import com.av.pixel.helper.UserHelper;
import com.av.pixel.mapper.UserCreditMap;
import com.av.pixel.mapper.UserMap;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public User createUser (UserDTO userDTO) {
        userHelper.validateNewUserRequest(userDTO);

        if (StringUtils.isEmpty(userDTO.getPassword())) {
            userDTO.setPassword(userHelper.getEncodedPassword());
        } else {
            userDTO.setPassword(userHelper.encodePassword(userDTO.getPassword()));
        }

        User user = UserMap.toUserEntity(userDTO);

        assert Objects.nonNull(user);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public SignUpResponse signUp (SignUpRequest signUpRequest) {
        UserDTO userDTO = UserMap.toUserDTO(signUpRequest);
        User user = createUser(userDTO);
        userDTO = UserMap.toUserDTO(user);

        UserCredit userCredit = userCreditService.createNewUserCredit(user);
        UserCreditDTO userCreditDTO = UserCreditMap.userCreditDTO(userCredit);

        UserTokenDTO userTokenDTO = null;
        if (StringUtils.isNotEmpty(signUpRequest.getAuthToken())) {
            userTokenDTO = userTokenService.registerToken(user.getCode(), signUpRequest.getAuthToken());
        } else {
            userTokenDTO = userTokenService.registerToken(user.getCode());
        }
        // TODO : cache
        return UserMap.toSignUpResponse(userDTO, userCreditDTO, userTokenDTO);
    }

    @Override
    @Transactional
    public SignInResponse signIn (SignInRequest signInRequest) {
        UserDTO userDTO = UserMap.toUserDTO(signInRequest);

        assert Objects.nonNull(userDTO);
        User user = userRepository.findByEmail(userDTO.getEmail());

        if (Objects.isNull(user)) {
            SignUpResponse signUpResponse = signUp(UserMap.toSignUpRequest(signInRequest));
            return UserMap.toSignInResponse(signUpResponse);
        }
        userDTO = UserMap.toUserDTO(user);

        UserCreditDTO userCreditDTO = userCreditService.getUserCredit(user);

        UserTokenDTO userTokenDTO = userTokenService.registerToken(user.getCode(), signInRequest.getAuthToken());
        // TODO : cache

        return UserMap.toResponse(userDTO, userCreditDTO, userTokenDTO);
    }

    @Override
    public String logout (String accessToken) {
        userTokenService.expireToken(accessToken);
        // TODO: clear cache
        return "SUCCESS";
    }
}
