package com.av.pixel.service;

import com.av.pixel.dto.UserTokenDTO;

public interface UserTokenService {

    UserTokenDTO registerToken(String userCode, String authToken);

    UserTokenDTO registerToken(String userCode);
}
