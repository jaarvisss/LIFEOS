package com.lifeos.module.profile.dto;

import java.time.LocalTime;

/**
 * Record DTO for updating work-life boundaries.
 */
public record WorkLifeBoundariesRequest(
        LocalTime workStartTime,
        LocalTime workEndTime,
        String workDays,
        LocalTime quietHoursStart,
        LocalTime quietHoursEnd
) {
}
