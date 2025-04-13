package com.av.pixel.enums;

import io.micrometer.common.util.StringUtils;

public enum AspectRatioEnum {
    ASPECT_10_16,
    ASPECT_16_10,
    ASPECT_9_16,
    ASPECT_16_9,
    ASPECT_3_2,
    ASPECT_2_3,
    ASPECT_4_3,
    ASPECT_3_4,
    ASPECT_1_1,
    ASPECT_1_3,
    ASPECT_3_1;

    public static AspectRatioEnum getEnumByName (String ratioName) {
        if (StringUtils.isEmpty(ratioName)) {
            return ASPECT_1_1;
        }
        for (AspectRatioEnum ratioEnum : AspectRatioEnum.values()) {
            if (ratioEnum.name().equalsIgnoreCase(ratioName)) {
                return ratioEnum;
            }
        }
        return ASPECT_1_1;
    }

}
