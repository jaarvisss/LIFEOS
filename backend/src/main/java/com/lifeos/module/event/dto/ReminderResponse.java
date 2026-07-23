package com.lifeos.module.event.dto;

import java.util.UUID;

/**
 * Record DTO for Reminder response.
 */
public record ReminderResponse(
        UUID id,
        UUID eventId,
        Integer minutesBefore,
        String channel
) {
}
