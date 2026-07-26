package com.lifeos.domain.ai.companion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Conversation Session Metadata.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationSummaryDTO {
    private UUID id;
    private String title;
    private String summary;
    private boolean favorite;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
