package com.lifeos.module.health.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Record DTO for Sleep log request.
 */
public record SleepLogRequest(
        @NotNull(message = "Sleep start time is required")
        Instant sleepStart,

        @NotNull(message = "Sleep end time is required")
        Instant sleepEnd,

        @Min(value = 1, message = "Quality score must be between 1 and 10")
        @Max(value = 10, message = "Quality score must be between 1 and 10")
        Integer qualityScore
) {
}
