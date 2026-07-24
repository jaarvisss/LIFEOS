package com.lifeos.module.search.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.search.dto.SearchRequest;
import com.lifeos.module.search.dto.SearchResultResponse;
import com.lifeos.module.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller for Cross-Module Full-Text Global Search.
 */
@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@Tag(name = "Global Search Engine", description = "Endpoints for searching across all domain entities (tasks, goals, contacts, notes, documents)")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    @Operation(summary = "Global Full-Text Search", description = "Searches tasks, notes, goals, contacts, and documents by title and content summary.")
    public ResponseEntity<ApiResponse<PagedResponse<SearchResultResponse>>> search(
            @RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "entityType", required = false) String entityType,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        SearchRequest request = new SearchRequest(query, entityType);
        return ResponseBuilder.ok(searchService.globalSearch(userId, request, paginationRequest));
    }
}
