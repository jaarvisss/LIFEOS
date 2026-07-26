package com.lifeos.domain.ai.companion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for Chat Message submission.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequestDTO {
    private UUID conversationId;
    private String message;
    private boolean stream = false;
}
