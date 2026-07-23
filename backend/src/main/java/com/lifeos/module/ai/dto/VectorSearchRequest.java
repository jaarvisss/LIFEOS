package com.lifeos.module.ai.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for Vector Similarity Search request.
 */
public record VectorSearchRequest(
        @NotBlank(message = "Query text is required")
        String queryText,

        Double matchThreshold,
        Integer limitCount
) {
}
