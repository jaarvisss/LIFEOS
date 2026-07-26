package com.lifeos.domain.ai.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Universal Search Query submission.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDTO {
    private String query;
    private List<String> domainFilters;
    private int limit = 20;
}
