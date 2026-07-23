package com.lifeos.module.analytics.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Life Score response.
 */
public record LifeScoreResponse(
        UUID id,
        UUID userId,
        LocalDate scoreDate,
        BigDecimal overallScore,
        BigDecimal healthSubscore,
        BigDecimal financeSubscore,
        BigDecimal productivitySubscore,
        BigDecimal relationshipSubscore
) {
}
