package com.av.pixel.mapper;

import com.av.pixel.dao.Generations;
import com.av.pixel.dao.ImageMetaData;
import com.av.pixel.dao.PromptImage;
import com.av.pixel.dto.GenerationsDTO;
import com.av.pixel.dto.PromptImageDTO;
import com.av.pixel.response.ideogram.ImageResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenerationsMap {

    public static List<GenerationsDTO> toList (List<Generations> generations){
        if (CollectionUtils.isEmpty(generations)) {
            return new ArrayList<>();
        }
        return generations.stream()
                .map(GenerationsMap::toGenerationsDTO)
                .toList();
    }

    public static GenerationsDTO toGenerationsDTO(Generations generations){
        if (Objects.isNull(generations)) {
            return null;
        }
        return new GenerationsDTO()
                .setImages(toPromptImageDTOList(generations.getImages()))
                .setTag(generations.getTag())
                .setCategory(generations.getCategory())
                .setUserCode(generations.getUserCode())
                .setModel(generations.getModel())
                .setUserPrompt(generations.getUserPrompt());
    }

    public static List<PromptImageDTO> toPromptImageDTOList (List<PromptImage> promptImages){
        if (CollectionUtils.isEmpty(promptImages)) {
            return new ArrayList<>();
        }
        return promptImages.stream()
                .map(GenerationsMap::toPromptImageDTO)
                .toList();
    }

    public static PromptImageDTO toPromptImageDTO(PromptImage promptImage) {
        if (Objects.isNull(promptImage)) {
            return null;
        }
        return new PromptImageDTO()
                .setImageId(promptImage.getImageId())
                .setMagicPrompt(promptImage.getMagicPrompt())
                .setLikes(promptImage.getLikes())
                .setUrl(promptImage.getUrl())
                .setStyle(promptImage.getStyle())
                .setMetaData(promptImage.getMetaData())
                .setResolution(promptImage.getResolution())
                .setPrivacy(promptImage.getPrivacy())
                .setSafeImage(promptImage.isSafeImage());
    }

    public static PromptImageDTO toPromptImageDTO(ImageResponse imageResponse, int index) {
        if (Objects.isNull(imageResponse)) {
            return null;
        }
        return new PromptImageDTO()
                .setImageId(index)
                .setMagicPrompt(imageResponse.getPrompt())
                .setLikes(0L)
                .setUrl(imageResponse.getUrl())
                .setStyle(imageResponse.getStyleType())
                .setMetaData(toImageMetaData(imageResponse))
                .setResolution(imageResponse.getResolution())
                .setPrivacy("PUBLIC")
                .setSafeImage(imageResponse.getIsImageSafe());
    }

    public static List<PromptImageDTO> toPromptImageList(List<ImageResponse> imageResponse) {
        if (CollectionUtils.isEmpty(imageResponse)) {
            return null;
        }

        int size = imageResponse.size();

        List<PromptImageDTO> promptImageDTOS = new ArrayList<>();

        for (int i=0;i<size;i++) {
            promptImageDTOS.add(toPromptImageDTO(imageResponse.get(i), i+1));
        }

        return promptImageDTOS;
    }

    public static GenerationsDTO toGenerationsDTO(String userCode, String model, String prompt, List<ImageResponse> imageResponses){
        if (CollectionUtils.isEmpty(imageResponses)) {
            return null;
        }
        return new GenerationsDTO()
                .setImages(toPromptImageList(imageResponses))
                .setTag(null)
                .setCategory(null)
                .setUserCode(userCode)
                .setModel(model)
                .setUserPrompt(prompt);
    }

    public static ImageMetaData toImageMetaData (ImageResponse imageResponse) {
        if (Objects.isNull(imageResponse)) {
            return null;
        }
        return new ImageMetaData()
                .setSeed(imageResponse.getSeed())
                .setResolution(imageResponse.getResolution());
    }

    public static Generations toEntity (GenerationsDTO generationsDTO) {
        if (Objects.isNull(generationsDTO)) {
            return null;
        }
        return new Generations()
                .setCategory(generationsDTO.getCategory())
                .setTag(generationsDTO.getTag())
                .setUserPrompt(generationsDTO.getUserPrompt())
                .setModel(generationsDTO.getModel())
                .setUserCode(generationsDTO.getUserCode())
                .setImages(toPromptImageEntityList(generationsDTO.getImages()));
    }

    public static List<PromptImage> toPromptImageEntityList (List<PromptImageDTO> images) {
        if (CollectionUtils.isEmpty(images)) {
            return null;
        }
        return images.stream()
                .map(GenerationsMap::toPromptImage)
                .toList();
    }

    public static PromptImage toPromptImage (PromptImageDTO promptImageDTO) {
        if (Objects.isNull(promptImageDTO)) {
            return null;
        }
        return new PromptImage()
                .setImageId(promptImageDTO.getImageId())
                .setUrl(promptImageDTO.getUrl())
                .setLikes(promptImageDTO.getLikes())
                .setStyle(promptImageDTO.getStyle())
                .setResolution(promptImageDTO.getResolution())
                .setPrivacy(promptImageDTO.getPrivacy())
                .setMetaData(promptImageDTO.getMetaData())
                .setSafeImage(promptImageDTO.isSafeImage());
    }
}
