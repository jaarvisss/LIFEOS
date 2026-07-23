package com.lifeos.module.event.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Record DTO for Reminder creation request.
 */
public record ReminderRequest(
        @NotNull(message = "Event ID is required")
        UUID eventId,

        Integer minutesBefore,
        String channel
) {
}
