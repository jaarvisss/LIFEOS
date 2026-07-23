package com.lifeos.module.health.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Record DTO for Body Metrics log request.
 */
public record BodyMetricsRequest(
        BigDecimal weightKg,
        BigDecimal heightCm,
        Integer restingHeartRate,
        LocalDate loggedDate
) {
}
