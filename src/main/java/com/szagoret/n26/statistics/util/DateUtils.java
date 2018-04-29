package com.szagoret.n26.statistics.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static Long secondsBefore(Long seconds) {
        return secondsBefore(seconds, Instant.now().toEpochMilli());
    }

    public static Long secondsBefore(Long seconds, Long startTimestamp) {
        return Instant.ofEpochMilli(startTimestamp).minus(seconds, ChronoUnit.SECONDS).toEpochMilli();
    }
}
