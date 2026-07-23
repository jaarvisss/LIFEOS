package com.lifeos.module.ai.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for AI Memory store/update request.
 */
public record AiMemoryRequest(
        @NotBlank(message = "Memory key is required")
        String memoryKey,

        @NotBlank(message = "Memory value is required")
        String memoryValue,

        String category,

        @Min(value = 1, message = "Importance score must be between 1 and 10")
        @Max(value = 10, message = "Importance score must be between 1 and 10")
        Integer importanceScore
) {
}
