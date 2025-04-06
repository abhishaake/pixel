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
@Table(name = "user_credit")
@Entity
@Accessors(chain = true)
public class UserCredit extends BaseEntity {

    @Column(name = "user_code")
    String userCode;

    @Column(name = "available")
    Long available;

    @Column(name = "utilised")
    Long utilised;
}
