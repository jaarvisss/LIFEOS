package com.lifeos.domain.memory.intelligence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Duplicate / Similarity Check.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DuplicateCheckResultDTO {
    private String status; // DUPLICATE, SIMILAR, RELATED, UNIQUE
    private double similarityScore;
    private String explanation;
}
