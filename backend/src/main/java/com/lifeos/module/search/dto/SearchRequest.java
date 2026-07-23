package com.lifeos.module.search.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for Global Full-Text Search request.
 */
public record SearchRequest(
        @NotBlank(message = "Search query is required")
        String query,

        String entityTypeFilter
) {
}
