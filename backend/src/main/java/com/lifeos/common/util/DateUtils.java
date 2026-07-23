package com.lifeos.common.util;

import com.lifeos.common.constants.CommonConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Enterprise Date Utilities for UTC and ISO-8601 formatting and parsing.
 */
public final class DateUtils {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern(CommonConstants.ISO_DATE_TIME_FORMAT)
            .withZone(ZoneOffset.UTC);

    private DateUtils() {
    }

    public static Instant now() {
        return Instant.now();
    }

    public static String formatIso(Instant instant) {
        if (instant == null) {
            return null;
        }
        return ISO_FORMATTER.format(instant);
    }

    public static Instant parseIso(String isoString) {
        if (isoString == null || isoString.isBlank()) {
            return null;
        }
        return Instant.from(ISO_FORMATTER.parse(isoString));
    }

    public static LocalDateTime toUtcLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    public static Instant toInstant(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    public static ZonedDateTime toZonedDateTime(Instant instant, String zoneId) {
        if (instant == null) {
            return null;
        }
        ZoneId zone = (zoneId != null && !zoneId.isBlank()) ? ZoneId.of(zoneId) : ZoneOffset.UTC;
        return instant.atZone(zone);
    }
}
