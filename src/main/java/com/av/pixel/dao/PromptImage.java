package com.av.pixel.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PromptImage {
    int imageId;
    String url;
    String magicPrompt;
    Long likes;
    String style;
    ImageMetaData metaData;
    String resolution;
    String privacy;
    boolean safeImage;
}
