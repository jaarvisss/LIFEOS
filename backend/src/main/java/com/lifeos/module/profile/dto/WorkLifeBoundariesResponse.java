package com.lifeos.module.profile.dto;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Record DTO for Work Life Boundaries response.
 */
public record WorkLifeBoundariesResponse(
        UUID id,
        UUID userId,
        LocalTime workStartTime,
        LocalTime workEndTime,
        String workDays,
        LocalTime quietHoursStart,
        LocalTime quietHoursEnd
) {
}
