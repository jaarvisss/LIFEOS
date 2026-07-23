package com.lifeos.module.knowledge.dto;

import java.util.UUID;

/**
 * Record DTO for Notebook response payload.
 */
public record NotebookResponse(
        UUID id,
        UUID userId,
        String name,
        String description
) {
}
