package com.av.pixel.dto;

import lombok.Data;

@Data
public class UserDTO {

    String firstName;
    String lastName;
    String email;
    String phone;
    String code;
    String password;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
