package com.lifeos.module.ai.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for AI Conversation creation request.
 */
public record AiConversationRequest(
        @NotBlank(message = "Title is required")
        String title,

        String systemInstruction,
        Boolean isPinned
) {
}
