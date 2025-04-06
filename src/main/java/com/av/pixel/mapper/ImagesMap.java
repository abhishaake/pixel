package com.av.pixel.mapper;

import com.av.pixel.dao.Images;
import com.av.pixel.dto.ImagesDTO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImagesMap {

    public static List<ImagesDTO> toList (List<Images> images){
        if (CollectionUtils.isEmpty(images)) {
            return new ArrayList<>();
        }
        return images.stream()
                .map(ImagesMap::toImagesDTO)
                .toList();
    }

    public static ImagesDTO toImagesDTO(Images images){
        if (Objects.isNull(images)) {
            return null;
        }

        return new ImagesDTO()
                .setId(images.getId())
                .setUrl(images.getUrl())
                .setStyle(images.getStyle())
                .setTag(images.getTag())
                .setCategory(images.getCategory())
                .setPrivacy(images.getPrivacy())
                .setUserCode(images.getUserCode())
                .setMetaData(images.getMetaData());
    }
}
