package com.lifeos.module.task.dto;

import java.util.UUID;

/**
 * Record DTO for Task Label response.
 */
public record TaskLabelResponse(
        UUID id,
        UUID userId,
        String name,
        String colorCode
) {
}
