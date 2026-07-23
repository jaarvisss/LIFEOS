package com.lifeos.module.ai.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.ai.dto.VectorSearchRequest;
import com.lifeos.module.ai.dto.VectorSearchResult;
import com.lifeos.module.ai.service.VectorSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for pgvector Semantic Vector Search.
 */
@RestController
@RequestMapping("/api/v1/ai/vector-search")
@RequiredArgsConstructor
@Tag(name = "AI Vector Search", description = "Endpoints for HNSW pgvector similarity search, embedding retrieval, and RAG context search")
public class VectorSearchController {

    private final VectorSearchService vectorSearchService;

    @PostMapping
    @Operation(summary = "Semantic Vector Search", description = "Performs pgvector cosine similarity search over 1536-dimensional chunk embeddings.")
    public ResponseEntity<ApiResponse<List<VectorSearchResult>>> searchSemanticVector(@Valid @RequestBody VectorSearchRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(vectorSearchService.searchSemanticVector(userId, request));
    }
}
