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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class IdeogramClient extends IdeogramBaseClient{

    @Value("")
    String url;

    final RestTemplate restTemplate;


    private static final List<String> ITEMS = Arrays.asList(
            "Apple", "Banana", "Cherry", "Date", "Elderberry"
    );

    private static final Random RANDOM = new Random();

    public List<ImageResponse> generateImages(ImageRequest imageRequest) {
        List<ImageResponse> res = new ArrayList<>();

        for(int i=0;i<imageRequest.getNumberOfImages();i++) {
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setPrompt("test magic prompt " + i +1);
            imageResponse.setResolution("1024x1024");
            imageResponse.setIsImageSafe(true);
            imageResponse.setSeed(Objects.isNull(imageResponse.getSeed()) ? 1234 : imageResponse.getSeed());
            imageResponse.setUrl(ITEMS.get(RANDOM.nextInt(ITEMS.size())));
            imageResponse.setStyleType(imageRequest.getStyleType().name());
            res.add(imageResponse);
        }

        return res;
    }

}
