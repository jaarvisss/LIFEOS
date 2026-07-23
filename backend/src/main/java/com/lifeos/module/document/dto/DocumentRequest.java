package com.lifeos.module.document.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for Document metadata creation request.
 */
public record DocumentRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "File extension is required")
        String fileExtension,

        @NotBlank(message = "MIME type is required")
        String mimeType,

        String storageProvider,

        @NotBlank(message = "Storage key is required")
        String storageKey,

        Long fileSizeBytes,
        String checksumSha256
) {
}
