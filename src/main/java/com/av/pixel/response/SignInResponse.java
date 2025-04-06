package com.av.pixel.response;

import lombok.Data;

@Data
public class SignInResponse {
    String firstName;
    String lastName;
    String fullName;
    String email;
    String phone;
    String code;
    String token;
    Long availableCredits;
    Long utilisedCredits;
}
