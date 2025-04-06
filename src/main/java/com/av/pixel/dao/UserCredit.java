package com.av.pixel.dao;

import com.av.pixel.dao.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCredit extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code")
    User user;

    @Column(name = "available")
    Long available;

    @Column(name = "utilised")
    Long utilised;
}
