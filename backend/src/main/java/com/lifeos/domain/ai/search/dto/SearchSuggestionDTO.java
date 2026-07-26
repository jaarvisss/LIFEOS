package com.lifeos.domain.ai.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Autocomplete & Search Suggestions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchSuggestionDTO {
    private String suggestionText;
    private String domainType;
    private String suggestionCategory; // RECENT, POPULAR, AI_RECOMMENDED
}
