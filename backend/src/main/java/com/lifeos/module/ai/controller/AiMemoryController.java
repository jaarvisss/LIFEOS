package com.lifeos.module.ai.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.ai.dto.AiMemoryRequest;
import com.lifeos.module.ai.dto.AiMemoryResponse;
import com.lifeos.module.ai.service.AiMemoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for AI Memory Engine.
 */
@RestController
@RequestMapping("/api/v1/ai/memory")
@RequiredArgsConstructor
@Tag(name = "AI Memory Engine", description = "Endpoints for user preference memory, contextual facts, and importance ranking")
public class AiMemoryController {

    private final AiMemoryService aiMemoryService;

    @PostMapping
    @Operation(summary = "Store Memory", description = "Stores a key-value contextual memory item for the AI engine.")
    public ResponseEntity<ApiResponse<AiMemoryResponse>> storeMemory(@Valid @RequestBody AiMemoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(aiMemoryService.storeMemory(userId, request), "Memory stored successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Memory by ID", description = "Retrieves memory item details.")
    public ResponseEntity<ApiResponse<AiMemoryResponse>> getMemoryById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiMemoryService.getMemoryById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Memories", description = "Retrieves user's contextual memory entries.")
    public ResponseEntity<ApiResponse<List<AiMemoryResponse>>> getAllMemories() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiMemoryService.getAllMemories(userId));
    }

    @GetMapping("/paged")
    @Operation(summary = "List Memories Paged", description = "Retrieves paginated memory entries.")
    public ResponseEntity<ApiResponse<PagedResponse<AiMemoryResponse>>> getMemoriesPaged(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiMemoryService.getMemoriesPaged(userId, paginationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Memory", description = "Updates memory value, category, or importance score.")
    public ResponseEntity<ApiResponse<AiMemoryResponse>> updateMemory(
            @PathVariable UUID id,
            @Valid @RequestBody AiMemoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiMemoryService.updateMemory(userId, id, request), "Memory updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Memory", description = "Soft-deletes a memory entry.")
    public ResponseEntity<ApiResponse<Void>> deleteMemory(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        aiMemoryService.deleteMemory(userId, id);
        return ResponseBuilder.ok(null, "Memory deleted successfully");
    }
}
