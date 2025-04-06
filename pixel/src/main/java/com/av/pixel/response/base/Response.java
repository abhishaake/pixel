package com.av.pixel.response.base;

import com.av.pixel.helper.DateUtil;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
public class Response<T> {

    Long serverTime = DateUtil.currentTimeSec();
    T data;
    boolean success;
    int statusCode;
    String message;
    String series;

    public Response() {
        this.success = true;
        this.statusCode = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.series = HttpStatus.OK.series().name();
    }

    public Response(T data) {
        this.data = data;
        this.success = true;
        this.statusCode = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.series = HttpStatus.OK.series().name();
    }

    public Response(HttpStatus httpStatus, T data) {
        this.data = data;
        this.success = true;
        this.statusCode = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.series = httpStatus.series().name();
    }
}
