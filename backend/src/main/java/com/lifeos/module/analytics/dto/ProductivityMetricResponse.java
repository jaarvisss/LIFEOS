package com.lifeos.module.analytics.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Productivity Metric response.
 */
public record ProductivityMetricResponse(
        UUID id,
        UUID userId,
        LocalDate metricDate,
        Integer tasksCompletedCount,
        Integer habitsCompletedCount,
        Integer focusMinutesTotal,
        BigDecimal productivityScore
) {
}
