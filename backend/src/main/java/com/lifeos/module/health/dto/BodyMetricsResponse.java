package com.lifeos.module.health.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Body Metrics response.
 */
public record BodyMetricsResponse(
        UUID id,
        UUID userId,
        BigDecimal weightKg,
        BigDecimal heightCm,
        Integer restingHeartRate,
        LocalDate loggedDate
) {
}
