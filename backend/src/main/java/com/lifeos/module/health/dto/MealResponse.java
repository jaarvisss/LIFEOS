package com.lifeos.module.health.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Meal response.
 */
public record MealResponse(
        UUID id,
        UUID userId,
        String mealType,
        Integer calories,
        String notes,
        Instant loggedAt
) {
}
