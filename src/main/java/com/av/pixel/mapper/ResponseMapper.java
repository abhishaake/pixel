package com.av.pixel.mapper;

import com.av.pixel.response.base.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {

    public static <T> ResponseEntity<Response<T>> response (T data, HttpStatus status) {
        return new ResponseEntity<>(new Response<>(status, data), status);
    }

}
