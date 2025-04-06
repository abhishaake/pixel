package com.av.pixel.dao;

import com.av.pixel.dao.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_token")
@Entity
@Accessors(chain = true)
public class UserToken extends BaseEntity {

    @Column(name = "user_code")
    String userCode;

    @Column(name = "accessToken")
    String accessToken;

    @Column(name = "authToken")
    String authToken;

    @Column(name = "validity")
    Long validity;

    @Column(name = "expired")
    boolean expired;
}
