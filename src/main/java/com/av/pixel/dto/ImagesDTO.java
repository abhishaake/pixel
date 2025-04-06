package com.av.pixel.dto;

import com.av.pixel.dao.ImageMetaData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ImagesDTO {
    Long id;
    @JsonProperty("user_code")
    String userCode;
    String url;
    String tag;
    String category;
    String style;
    String privacy;
    ImageMetaData metaData;
}
