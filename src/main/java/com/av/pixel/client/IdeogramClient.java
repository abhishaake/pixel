package com.av.pixel.client;

import com.av.pixel.request.ideogram.ImageRequest;
import com.av.pixel.response.ideogram.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class IdeogramClient extends IdeogramBaseClient{

    @Value("")
    String url;

    final RestTemplate restTemplate;


    public List<ImageResponse> generateImages(ImageRequest imageRequest) {
        List<ImageResponse> res = new ArrayList<>();

        return null;
    }

}
