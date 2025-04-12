package com.av.pixel.request.ideogram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
public class ImageRequest {

    String prompt;

    @JsonProperty("aspect_ratio")
    String aspectRatio;

    String model;

    @JsonProperty("magic_prompt_option")
    String magicPromptOption;

    Long seed;

    @JsonProperty("style_type")
    String styleType;

    @JsonProperty("negative_prompt")
    String negativePrompt;

    @JsonProperty("num_images")
    Integer numberOfImages;

    String resolution;
}
