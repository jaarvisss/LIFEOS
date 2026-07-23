package com.lifeos.module.search.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.search.dto.SearchRequest;
import com.lifeos.module.search.dto.SearchResultResponse;

import java.util.UUID;

/**
 * Service Interface for Cross-Module Global Search & Full-Text Indexing.
 */
public interface SearchService {

    PagedResponse<SearchResultResponse> globalSearch(UUID userId, SearchRequest request, PaginationRequest paginationRequest);

    void indexEntity(UUID userId, String entityType, UUID entityId, String title, String contentSummary, String tags);

    void deindexEntity(UUID userId, String entityType, UUID entityId);
}
