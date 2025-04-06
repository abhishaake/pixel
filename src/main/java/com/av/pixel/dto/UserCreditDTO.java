package com.av.pixel.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserCreditDTO {
    String userCode;
    Long available;
    Long utilised;
}
