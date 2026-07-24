package com.lifeos.module.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Event creation/update request.
 */
public record EventRequest(
        UUID calendarId,

        @NotBlank(message = "Title is required")
        String title,

        String description,
        String location,

        @NotNull(message = "Start time is required")
        Instant startTime,

        @NotNull(message = "End time is required")
        Instant endTime,

        Boolean isAllDay,
        String eventStatus
) {
}
