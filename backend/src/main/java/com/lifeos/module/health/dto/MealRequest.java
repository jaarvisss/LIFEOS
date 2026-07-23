package com.lifeos.module.health.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

/**
 * Record DTO for Meal log request.
 */
public record MealRequest(
        @NotBlank(message = "Meal type is required")
        String mealType,

        Integer calories,
        String notes,
        Instant loggedAt
) {
}
