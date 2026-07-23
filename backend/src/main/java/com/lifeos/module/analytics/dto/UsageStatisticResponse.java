package com.lifeos.module.analytics.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Usage Statistic response.
 */
public record UsageStatisticResponse(
        UUID id,
        UUID userId,
        String featureName,
        Integer actionCount,
        Instant lastUsedAt
) {
}
