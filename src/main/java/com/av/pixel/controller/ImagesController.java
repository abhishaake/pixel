package com.av.pixel.controller;

import com.av.pixel.auth.Authenticated;
import com.av.pixel.dto.UserDTO;
import com.av.pixel.request.ImageFilterRequest;
import com.av.pixel.response.ImageFilterResponse;
import com.av.pixel.response.base.Response;
import com.av.pixel.service.ImagesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.av.pixel.mapper.ResponseMapper.response;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("api/images")
public class ImagesController {

    ImagesService imagesService;

    @PostMapping("/filter")
    @Authenticated
    public ResponseEntity<Response<ImageFilterResponse>> filterImages(UserDTO userDTO,
                                                                      @RequestBody ImageFilterRequest imageFilterRequest) {
        return response(imagesService.filterImages(imageFilterRequest), HttpStatus.OK);
    }

}
