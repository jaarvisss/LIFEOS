package com.lifeos.module.calendar.dto;

import java.util.UUID;

/**
 * Record DTO for Calendar response.
 */
public record CalendarResponse(
        UUID id,
        UUID userId,
        String name,
        String description,
        String colorCode,
        Boolean isDefault
) {
}
