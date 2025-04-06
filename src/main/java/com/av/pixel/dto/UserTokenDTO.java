package com.av.pixel.dto;

import lombok.Data;

@Data
public class UserTokenDTO {

    String userCode;
    String authToken;
    String accessToken;
    Long validity;
    boolean expired;
}
