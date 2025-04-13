package com.av.pixel.enums;

import io.micrometer.common.util.StringUtils;

public enum ImageStyleEnum {
    AUTO,
    GENERAL,
    REALISTIC,
    DESIGN,
    RENDER_3D,
    ANIME;

    public static ImageStyleEnum getEnumByName (String style) {
        if (StringUtils.isEmpty(style)) {
            return AUTO;
        }
        for (ImageStyleEnum styleEnum : ImageStyleEnum.values()) {
            if (styleEnum.name().equalsIgnoreCase(style)) {
                return styleEnum;
            }
        }
        return AUTO;
    }
}
