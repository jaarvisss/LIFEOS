package com.lifeos.module.knowledge.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Note response payload.
 */
public record NoteResponse(
        UUID id,
        UUID userId,
        UUID notebookId,
        String title,
        String content,
        Boolean isFavorite,
        Instant createdAt,
        Instant updatedAt
) {
}
