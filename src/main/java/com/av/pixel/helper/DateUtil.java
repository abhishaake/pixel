package com.av.pixel.helper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static Long currentTimeSec() {
        return System.currentTimeMillis() / 1000;
    }

    public static Long getXYearAheadEpoch(int years) {
        Instant now = Instant.now();
        ZonedDateTime oneYearLater = now.atZone(ZoneId.systemDefault()).plusYears(1);
        return oneYearLater.toInstant().toEpochMilli();
    }
}
