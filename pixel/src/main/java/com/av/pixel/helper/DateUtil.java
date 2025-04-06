package com.av.pixel.helper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static Long currentTimeSec() {
        return System.currentTimeMillis() / 1000;
    }

    public static Long getXYearAheadEpoch(int years) {
        Instant now = Instant.now();
        Instant xYearLater = now.plus(years, ChronoUnit.YEARS);

        return xYearLater.toEpochMilli();
    }
}
