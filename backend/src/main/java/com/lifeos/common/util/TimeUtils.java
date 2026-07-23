package com.lifeos.common.util;

import java.time.Duration;
import java.time.Instant;

/**
 * Enterprise Time & Duration Utilities.
 */
public final class TimeUtils {

    private TimeUtils() {
    }

    public static long getDurationInSeconds(Instant start, Instant end) {
        if (start == null || end == null) {
            return 0;
        }
        return Duration.between(start, end).getSeconds();
    }

    public static long getDurationInMillis(Instant start, Instant end) {
        if (start == null || end == null) {
            return 0;
        }
        return Duration.between(start, end).toMillis();
    }

    public static boolean isExpired(Instant expirationTime) {
        if (expirationTime == null) {
            return true;
        }
        return Instant.now().isAfter(expirationTime);
    }
}
