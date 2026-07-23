package com.lifeos.module.goal.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Record DTO for updating goal progress.
 */
public record GoalProgressRequest(
        @NotNull(message = "Progress percentage is required")
        @DecimalMin(value = "0.00", message = "Progress percentage must be at least 0.00")
        @DecimalMax(value = "100.00", message = "Progress percentage cannot exceed 100.00")
        BigDecimal progressPercentage,

        String status
) {
}
