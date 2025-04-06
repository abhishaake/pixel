package com.av.pixel.service.impl;

import com.av.pixel.dao.UserToken;
import com.av.pixel.dto.UserTokenDTO;
import com.av.pixel.helper.UserTokenHelper;
import com.av.pixel.mapper.UserMapper;
import com.av.pixel.repository.UserTokenRepository;
import com.av.pixel.service.UserTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    UserTokenRepository userTokenRepository;

    @Override
    public UserTokenDTO registerToken (String userCode, String authToken) {
        UserToken userToken = new UserToken();
        userToken.setAccessToken(UserTokenHelper.generateToken());
        userToken.setAuthToken(authToken);
        userToken.setUserCode(userCode);
        userToken.setValidity(UserTokenHelper.getDefaultValidity());
        userToken.setExpired(false);
        userToken = userTokenRepository.save(userToken);
        return UserMapper.INSTANCE.toTokenDTO(userToken);
    }

    @Override
    public UserTokenDTO registerToken (String userCode) {
        UserToken userToken = new UserToken();
        userToken.setAccessToken(UserTokenHelper.generateToken());
        userToken.setUserCode(userCode);
        userToken.setValidity(UserTokenHelper.getDefaultValidity());
        userToken.setExpired(false);
        userToken = userTokenRepository.save(userToken);
        return UserMapper.INSTANCE.toTokenDTO(userToken);
    }
}
