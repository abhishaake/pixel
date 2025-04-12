package com.av.pixel.mapper.ideogram;

import com.av.pixel.request.GenerateRequest;
import com.av.pixel.request.ideogram.ImageRequest;

import java.util.Objects;

public class ImageMap {

    public static ImageRequest toImageRequest(GenerateRequest generateRequest) {
        if (Objects.isNull(generateRequest)) {
            return null;
        }
        return new ImageRequest()
                .setNumberOfImages(generateRequest.getNumberOfImages())
                .setAspectRatio(generateRequest.getAspectRatio())
                .setModel(generateRequest.getModel())
                .setMagicPromptOption(generateRequest.getMagicPromptOption())
                .setPrompt(generateRequest.getPrompt())
                .setSeed(generateRequest.getSeed())
                .setNegativePrompt(generateRequest.getNegativePrompt())
                .setResolution(generateRequest.getResolution())
                .setStyleType(generateRequest.getStyleType());
    }
}
