package com.lifeos.module.document.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Document response.
 */
public record DocumentResponse(
        UUID id,
        UUID userId,
        String title,
        String fileExtension,
        String mimeType,
        String storageProvider,
        String storageKey,
        Instant createdAt,
        Instant updatedAt
) {
}
