package com.av.pixel.mapper;

import com.av.pixel.dao.UserCredit;
import com.av.pixel.dto.UserCreditDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserCreditMapper {

    UserCreditMapper INSTANCE = Mappers.getMapper(UserCreditMapper.class);

    UserCredit toEntity(UserCreditDTO userCreditDTO);

    UserCreditDTO toDTO(UserCredit userCredit);
}
