package com.lifeos.module.search.dto;

import java.util.UUID;

/**
 * Record DTO for Search result entry.
 */
public record SearchResultResponse(
        UUID id,
        String entityType,
        UUID entityId,
        String title,
        String contentSummary,
        String tags
) {
}
