package com.av.pixel.dto;

import com.av.pixel.dao.ImageMetaData;
import com.av.pixel.dao.PromptImage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenerationsDTO {
    String generationId;
    String userCode;
    String userName;
    String userImgUrl;
    List<PromptImageDTO> images;
    String tag;
    String category;
    String model;
    String userPrompt;
    Long likes;
    String renderOption;
    Long seed;
    String resolution;
    Boolean privateImage;
    String style;
    boolean selfLike;
}
