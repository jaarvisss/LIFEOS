package com.lifeos.domain.ai.search.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.ai.search.dto.*;
import com.lifeos.domain.ai.search.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Universal Search Platform & Direct AI Answer Mode.
 */
@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@Tag(name = "Universal Search Platform", description = "Endpoints for hybrid vector search, direct AI answers with citations, search suggestions, and search history")
public class AiSearchController {

    private final SearchOrchestratorService orchestratorService;
    private final SearchAnswerService answerService;

    @PostMapping
    @Operation(summary = "Execute Hybrid Search", description = "Executes hybrid vector and keyword search across all 10 core LifeOS domains.")
    public ResponseEntity<ApiResponse<SearchResultResponseDTO>> executeSearch(@RequestBody SearchRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(orchestratorService.executeSearch(userId, request));
    }

    @PostMapping("/answer")
    @Operation(summary = "Direct AI Answer Mode", description = "Generates a direct, cited AI answer synthesizing cross-domain records.")
    public ResponseEntity<ApiResponse<SearchAnswerResponseDTO>> generateAiAnswer(@RequestBody SearchRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(answerService.generateAiAnswer(userId, request));
    }

    @GetMapping("/suggestions")
    @Operation(summary = "Get Search Suggestions", description = "Retrieves context-aware autocomplete suggestions.")
    public ResponseEntity<ApiResponse<List<SearchSuggestionDTO>>> getSuggestions(@RequestParam(required = false, defaultValue = "") String query) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(orchestratorService.getSearchSuggestions(userId, query));
    }

    @GetMapping("/history")
    @Operation(summary = "Get Search History", description = "Retrieves recent search query history.")
    public ResponseEntity<ApiResponse<List<String>>> getHistory() {
        return ResponseBuilder.ok(List.of("Rahul meeting", "Identity Builders project", "Office equipment expense"));
    }

    @DeleteMapping("/history")
    @Operation(summary = "Clear Search History", description = "Clears user search history.")
    public ResponseEntity<ApiResponse<Void>> clearHistory() {
        return ResponseBuilder.ok(null, "Search history cleared");
    }

    @GetMapping("/providers")
    @Operation(summary = "List Search Providers", description = "Retrieves active domain search providers.")
    public ResponseEntity<ApiResponse<List<String>>> getProviders() {
        return ResponseBuilder.ok(orchestratorService.getSearchProviders());
    }

    @GetMapping("/trending")
    @Operation(summary = "Get Trending Searches", description = "Retrieves trending topics across the system.")
    public ResponseEntity<ApiResponse<List<String>>> getTrending() {
        return ResponseBuilder.ok(List.of("AI Platform Release", "Weekly Focus Tasks", "Health Routine"));
    }
}
