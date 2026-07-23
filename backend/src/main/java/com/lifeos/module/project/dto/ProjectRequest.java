package com.lifeos.module.project.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Project creation/update payload.
 */
public record ProjectRequest(
        @NotBlank(message = "Project name is required")
        String name,

        String description,
        UUID goalId,
        String status,
        String colorCode,
        LocalDate startDate,
        LocalDate targetEndDate
) {
}
