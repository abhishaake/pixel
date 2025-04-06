package com.av.pixel.controller;

import com.av.pixel.response.base.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/utility")
public class UtilityController {

    @GetMapping("/health")
    public Response<String> health() {
        return new Response<>(HttpStatus.OK, "success");
    }
}
