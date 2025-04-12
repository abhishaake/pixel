package com.av.pixel.dto;

import com.av.pixel.dao.ImageMetaData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GenerationsDTO {
    String userCode;
    List<PromptImageDTO> images;
    String tag;
    String category;
    String model;
    String userPrompt;
}
