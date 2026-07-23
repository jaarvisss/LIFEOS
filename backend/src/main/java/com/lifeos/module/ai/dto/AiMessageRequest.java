package com.lifeos.module.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Record DTO for AI Message request payload.
 */
public record AiMessageRequest(
        @NotNull(message = "Conversation ID is required")
        UUID conversationId,

        @NotBlank(message = "Content is required")
        String content
) {
}
