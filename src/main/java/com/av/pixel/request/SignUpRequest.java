package com.av.pixel.request;

import lombok.Data;

@Data
public class SignUpRequest {
    String firstName;
    String lastName;
    String email;
    String phone;
    String code;
    String password;
    String authToken;
}
