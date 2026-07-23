package com.lifeos.module.task.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for Task Label creation payload.
 */
public record TaskLabelRequest(
        @NotBlank(message = "Label name is required")
        String name,

        String colorCode
) {
}
