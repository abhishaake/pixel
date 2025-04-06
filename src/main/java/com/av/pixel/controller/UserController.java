package com.av.pixel.controller;

import com.av.pixel.request.SignUpRequest;
import com.av.pixel.response.SignUpResponse;
import com.av.pixel.response.base.Response;
import com.av.pixel.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    UserService userService;


    @PostMapping("/signUp")
    public Response<SignUpResponse> signUp (@RequestBody SignUpRequest signUpRequest) {
        return new Response<>(HttpStatus.CREATED, userService.signUp(signUpRequest));
    }
}
