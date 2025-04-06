package com.av.pixel.dto;

import lombok.Data;

@Data
public class UserCreditDTO {
    String userCode;
    Long available;
    Long utilised;
}
