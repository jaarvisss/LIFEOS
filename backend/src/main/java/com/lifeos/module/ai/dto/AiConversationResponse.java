package com.lifeos.module.ai.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for AI Conversation response.
 */
public record AiConversationResponse(
        UUID id,
        UUID userId,
        String title,
        String systemInstruction,
        Boolean isPinned,
        Instant createdAt,
        Instant updatedAt
) {
}
