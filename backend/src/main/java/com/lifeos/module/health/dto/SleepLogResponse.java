package com.lifeos.module.health.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Sleep log response.
 */
public record SleepLogResponse(
        UUID id,
        UUID userId,
        Instant sleepStart,
        Instant sleepEnd,
        Integer qualityScore
) {
}
