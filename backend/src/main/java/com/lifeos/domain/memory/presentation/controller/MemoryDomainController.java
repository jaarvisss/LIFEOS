package com.lifeos.domain.memory.presentation.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.memory.application.service.MemoryService;
import com.lifeos.domain.memory.presentation.request.CreateMemoryRequest;
import com.lifeos.domain.memory.presentation.request.UpdateMemoryRequest;
import com.lifeos.domain.memory.presentation.response.MemoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Memory Engine Aggregate Root.
 */
@RestController
@RequestMapping("/api/v1/memories")
@RequiredArgsConstructor
@Tag(name = "Memory Engine Foundation", description = "Endpoints for creating, organizing, pinning, archiving, and retrieving personal memories")
public class MemoryDomainController {

    private final MemoryService memoryService;

    @PostMapping
    @Operation(summary = "Create Memory", description = "Creates a new contextual memory entry.")
    public ResponseEntity<ApiResponse<MemoryResponse>> createMemory(@Valid @RequestBody CreateMemoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(memoryService.createMemory(userId, request), "Memory created successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Memory", description = "Updates memory title, content, summary, or importance.")
    public ResponseEntity<ApiResponse<MemoryResponse>> updateMemory(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMemoryRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.updateMemory(userId, id, request), "Memory updated successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Memory by ID", description = "Retrieves memory item details and records access count.")
    public ResponseEntity<ApiResponse<MemoryResponse>> getMemoryById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.getMemoryById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Memories", description = "Retrieves paginated user memories filtered by status or type.")
    public ResponseEntity<ApiResponse<PagedResponse<MemoryResponse>>> getAllMemories(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "type", required = false) String type,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.getAllMemories(userId, status, type, paginationRequest.getPage(), paginationRequest.getSize()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Memory", description = "Soft-deletes a memory entry.")
    public ResponseEntity<ApiResponse<Void>> deleteMemory(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        memoryService.deleteMemory(userId, id);
        return ResponseBuilder.ok(null, "Memory deleted successfully");
    }

    @PostMapping("/{id}/archive")
    @Operation(summary = "Archive Memory", description = "Archives an active memory entry.")
    public ResponseEntity<ApiResponse<MemoryResponse>> archiveMemory(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.archiveMemory(userId, id), "Memory archived successfully");
    }

    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore Memory", description = "Restores an archived or deleted memory entry to active status.")
    public ResponseEntity<ApiResponse<MemoryResponse>> restoreMemory(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.restoreMemory(userId, id), "Memory restored successfully");
    }

    @PostMapping("/{id}/favorite")
    @Operation(summary = "Favorite Memory", description = "Toggles favorite status for a memory entry.")
    public ResponseEntity<ApiResponse<MemoryResponse>> favoriteMemory(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.favoriteMemory(userId, id), "Memory favorite status updated");
    }

    @PostMapping("/{id}/pin")
    @Operation(summary = "Pin Memory", description = "Toggles pinned status for a memory entry.")
    public ResponseEntity<ApiResponse<MemoryResponse>> pinMemory(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.pinMemory(userId, id), "Memory pinned status updated");
    }

    @GetMapping("/recent")
    @Operation(summary = "Get Recent Memories", description = "Retrieves top 10 recently accessed memories.")
    public ResponseEntity<ApiResponse<List<MemoryResponse>>> getRecentMemories() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.getRecentMemories(userId));
    }

    @GetMapping("/favorites")
    @Operation(summary = "Get Favorite Memories", description = "Retrieves user's favorite memories.")
    public ResponseEntity<ApiResponse<List<MemoryResponse>>> getFavoriteMemories() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.getFavoriteMemories(userId));
    }

    @GetMapping("/pinned")
    @Operation(summary = "Get Pinned Memories", description = "Retrieves user's pinned memories.")
    public ResponseEntity<ApiResponse<List<MemoryResponse>>> getPinnedMemories() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(memoryService.getPinnedMemories(userId));
    }
}
