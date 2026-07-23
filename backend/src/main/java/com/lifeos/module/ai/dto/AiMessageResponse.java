package com.lifeos.module.ai.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for AI Message response payload.
 */
public record AiMessageResponse(
        UUID id,
        UUID conversationId,
        String role,
        String content,
        Integer tokenCount,
        Instant createdAt
) {
}
