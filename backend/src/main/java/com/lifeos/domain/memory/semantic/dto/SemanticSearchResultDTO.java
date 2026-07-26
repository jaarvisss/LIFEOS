package com.lifeos.domain.memory.semantic.dto;

import com.lifeos.domain.memory.presentation.response.MemoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Semantic & Hybrid Search Results.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemanticSearchResultDTO {
    private MemoryResponse memory;
    private double similarityScore;
    private double rankingScore;
    private String matchExplanation;
}
