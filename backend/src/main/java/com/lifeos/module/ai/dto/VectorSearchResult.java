package com.lifeos.module.ai.dto;

import java.util.UUID;

/**
 * Record DTO for pgvector similarity search result.
 */
public record VectorSearchResult(
        UUID id,
        String sourceType,
        UUID sourceId,
        String chunkContent,
        Double similarity
) {
}
