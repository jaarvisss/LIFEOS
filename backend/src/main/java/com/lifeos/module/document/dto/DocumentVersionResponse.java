package com.lifeos.module.document.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Document Version response.
 */
public record DocumentVersionResponse(
        UUID id,
        UUID documentId,
        Integer versionNumber,
        Long fileSizeBytes,
        String checksumSha256,
        String storageKey,
        Instant createdAt
) {
}
