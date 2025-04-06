package com.av.pixel.response;

import lombok.Data;

@Data
public class SignUpResponse {
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
