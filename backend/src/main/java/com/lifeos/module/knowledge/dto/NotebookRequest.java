package com.lifeos.module.knowledge.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for Notebook creation/update payload.
 */
public record NotebookRequest(
        @NotBlank(message = "Notebook name is required")
        String name,

        String description
) {
}
