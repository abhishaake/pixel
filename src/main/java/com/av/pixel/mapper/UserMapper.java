package com.av.pixel.mapper;

import com.av.pixel.dao.User;
import com.av.pixel.dao.UserToken;
import com.av.pixel.dto.UserDTO;
import com.av.pixel.dto.UserTokenDTO;
import com.av.pixel.request.SignInRequest;
import com.av.pixel.request.SignUpRequest;
import com.av.pixel.response.SignInResponse;
import com.av.pixel.response.SignUpResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity (UserDTO userDTO);

    UserDTO toDTO (User user);

    UserDTO toDTO (SignUpRequest signUpRequest);

    UserDTO toDTO (SignInRequest signInRequest);

    SignUpResponse toResponse (UserDTO userDTO);

    SignUpRequest toSignUpRequest(SignInRequest signInRequest);

    SignInResponse toSignInResponse(SignUpResponse signUpResponse);

    UserTokenDTO toTokenDTO(UserToken userToken);

    SignInResponse toSignInResponse (UserDTO userDTO);

}
