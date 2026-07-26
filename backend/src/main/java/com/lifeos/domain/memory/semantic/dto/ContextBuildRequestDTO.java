package com.lifeos.domain.memory.semantic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for Context Builder Service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextBuildRequestDTO {
    private String prompt;
    private int maxTokenBudget = 2048;
    private int maxMemories = 5;
}
