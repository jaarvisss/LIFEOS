package com.lifeos.module.ai.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for AI Suggestion response payload.
 */
public record AiSuggestionResponse(
        UUID id,
        UUID userId,
        String title,
        String suggestionText,
        String category,
        BigDecimal confidenceScore,
        Boolean isAccepted,
        Instant createdAt
) {
}
