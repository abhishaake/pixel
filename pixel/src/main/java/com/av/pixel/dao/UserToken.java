package com.av.pixel.dao;

import com.av.pixel.dao.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserToken extends BaseEntity {

    String userCode;

    String accessToken;

    String authToken;

    Long validity;

    boolean expired;
}
