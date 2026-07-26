package com.lifeos.domain.ai.companion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for Conversation Feedback.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDTO {
    private UUID messageId;
    private String feedbackType; // THUMBS_UP, THUMBS_DOWN, HALLUCINATION
    private String comment;
}
