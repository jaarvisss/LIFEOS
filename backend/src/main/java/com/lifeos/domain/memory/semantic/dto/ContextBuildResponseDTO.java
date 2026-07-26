package com.lifeos.domain.memory.semantic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO containing formatted LLM prompt context block.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextBuildResponseDTO {
    private String formattedContextBlock;
    private List<SemanticSearchResultDTO> retrievedMemories;
    private int estimatedTokens;
    private long retrievalLatencyMs;
}
