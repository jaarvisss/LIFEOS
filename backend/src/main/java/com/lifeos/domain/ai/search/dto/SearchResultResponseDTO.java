package com.lifeos.domain.ai.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Universal Search Result Set.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultResponseDTO {
    private String query;
    private String intentType; // LOOKUP, QUESTION, TIMELINE, COMPARISON
    private int totalResults;
    private List<SearchResultItemDTO> items;
}
