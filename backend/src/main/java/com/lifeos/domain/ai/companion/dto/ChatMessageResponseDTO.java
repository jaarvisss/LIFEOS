package com.lifeos.domain.ai.companion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Response DTO for Chat Message & AI Companion response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDTO {
    private UUID conversationId;
    private UUID messageId;
    private String senderType; // ASSISTANT
    private String content;
    private String intentType;
    private double confidenceScore;
    private List<String> citedMemories;
    private List<SuggestedActionDTO> suggestedActions;
    private LocalDateTime timestamp;
}
