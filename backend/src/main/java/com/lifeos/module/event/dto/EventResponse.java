package com.lifeos.module.event.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Event response.
 */
public record EventResponse(
        UUID id,
        UUID calendarId,
        String title,
        String description,
        String location,
        Instant startTime,
        Instant endTime,
        Boolean isAllDay,
        String eventStatus,
        Instant createdAt,
        Instant updatedAt
) {
}
