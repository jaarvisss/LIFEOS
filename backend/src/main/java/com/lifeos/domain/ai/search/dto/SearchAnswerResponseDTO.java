package com.lifeos.domain.ai.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Direct AI Answer Mode with Citations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchAnswerResponseDTO {
    private String query;
    private String directAnswer;
    private double confidenceScore;
    private List<SearchResultItemDTO> supportingRecords;
    private List<String> suggestedNextActions;
}
