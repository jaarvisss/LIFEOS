package com.lifeos.module.knowledge.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * Record DTO for Note creation/update payload.
 */
public record NoteRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Content is required")
        String content,

        UUID notebookId,
        Boolean isFavorite
) {
}
