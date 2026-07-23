package com.lifeos.module.ai.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for AI Memory response payload.
 */
public record AiMemoryResponse(
        UUID id,
        UUID userId,
        String memoryKey,
        String memoryValue,
        String category,
        Integer importanceScore,
        Instant createdAt
) {
}
