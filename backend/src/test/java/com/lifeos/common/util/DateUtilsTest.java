package com.lifeos.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    @DisplayName("Should format and parse Instant to ISO-8601 string correctly")
    void testIsoFormattingAndParsing() {
        Instant now = Instant.parse("2026-07-23T12:34:56.789Z");
        String formatted = DateUtils.formatIso(now);

        assertEquals("2026-07-23T12:34:56.789Z", formatted);

        Instant parsed = DateUtils.parseIso(formatted);
        assertEquals(now, parsed);
    }

    @Test
    @DisplayName("Should convert Instant to UTC LocalDateTime and back")
    void testLocalDateTimeConversion() {
        Instant now = Instant.parse("2026-07-23T10:00:00.000Z");
        LocalDateTime localDateTime = DateUtils.toUtcLocalDateTime(now);

        assertNotNull(localDateTime);
        assertEquals(2026, localDateTime.getYear());
        assertEquals(7, localDateTime.getMonthValue());
        assertEquals(23, localDateTime.getDayOfMonth());

        Instant convertedBack = DateUtils.toInstant(localDateTime);
        assertEquals(now, convertedBack);
    }
}
