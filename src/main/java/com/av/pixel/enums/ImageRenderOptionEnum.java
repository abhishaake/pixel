package com.av.pixel.enums;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ImageRenderOptionEnum {

    TURBO("turbo"),
    QUALITY("quality");

    final String value;

    public static ImageRenderOptionEnum getEnumByName (String renderOption) {
        if (StringUtils.isEmpty(renderOption)) {
            return TURBO;
        }
        for (ImageRenderOptionEnum optionEnum : ImageRenderOptionEnum.values()) {
            if (optionEnum.value.equalsIgnoreCase(renderOption)) {
                return optionEnum;
            }
        }
        return TURBO;
    }
}
