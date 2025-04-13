package com.av.pixel.enums;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PixelModelEnum {

    PIXEL_V1(IdeogramModelEnum.V_1_TURBO, IdeogramModelEnum.V_1),
    PIXEL_V2(IdeogramModelEnum.V_2_TURBO, IdeogramModelEnum.V_2),
    PIXEL_V2A(IdeogramModelEnum.V_2A_TURBO, IdeogramModelEnum.V_2A);

    final IdeogramModelEnum turboModel;
    final IdeogramModelEnum qualityModel;

    public static IdeogramModelEnum getIdeogramModelByNameAndRenderOption (String modelName, ImageRenderOptionEnum renderOptionEnum) {
        if (StringUtils.isEmpty(modelName)) {
            return null;
        }
        for (PixelModelEnum modelEnum : PixelModelEnum.values()) {
            if (modelEnum.name().equalsIgnoreCase(modelName)) {
                if (ImageRenderOptionEnum.QUALITY.equals(renderOptionEnum)) {
                    return modelEnum.qualityModel;
                }
                return modelEnum.turboModel;
            }
        }
        return null;
    }
}
