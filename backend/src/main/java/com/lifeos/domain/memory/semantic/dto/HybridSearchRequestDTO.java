package com.lifeos.domain.memory.semantic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request DTO for Hybrid Vector & Keyword Search.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HybridSearchRequestDTO {
    private String query;
    private int topK = 10;
    private String memoryType;
    private String importanceLevel;
    private List<String> tags;
    private Double minSimilarity = 0.6;
}
