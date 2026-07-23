package com.lifeos.module.calendar.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for Calendar creation/update request.
 */
public record CalendarRequest(
        @NotBlank(message = "Calendar name is required")
        String name,

        String description,
        String colorCode,
        Boolean isDefault
) {
}
