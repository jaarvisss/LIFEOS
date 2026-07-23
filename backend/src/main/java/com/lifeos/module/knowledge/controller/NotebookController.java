package com.lifeos.module.knowledge.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.knowledge.dto.NotebookRequest;
import com.lifeos.module.knowledge.dto.NotebookResponse;
import com.lifeos.module.knowledge.service.NotebookService;
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
 * REST Controller for Knowledge Notebooks.
 */
@RestController
@RequestMapping("/api/v1/notebooks")
@RequiredArgsConstructor
@Tag(name = "Knowledge Notebooks", description = "Endpoints for notebook categorization of Markdown notes")
public class NotebookController {

    private final NotebookService notebookService;

    @PostMapping
    @Operation(summary = "Create Notebook", description = "Creates a new notebook.")
    public ResponseEntity<ApiResponse<NotebookResponse>> createNotebook(@Valid @RequestBody NotebookRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(notebookService.createNotebook(userId, request), "Notebook created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Notebook by ID", description = "Retrieves notebook details.")
    public ResponseEntity<ApiResponse<NotebookResponse>> getNotebookById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(notebookService.getNotebookById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Notebooks", description = "Retrieves user's notebooks.")
    public ResponseEntity<ApiResponse<List<NotebookResponse>>> getNotebooks() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(notebookService.getNotebooksByUserId(userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Notebook", description = "Updates notebook details.")
    public ResponseEntity<ApiResponse<NotebookResponse>> updateNotebook(
            @PathVariable UUID id,
            @Valid @RequestBody NotebookRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(notebookService.updateNotebook(userId, id, request), "Notebook updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Notebook", description = "Soft-deletes a notebook.")
    public ResponseEntity<ApiResponse<Void>> deleteNotebook(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        notebookService.deleteNotebook(userId, id);
        return ResponseBuilder.ok(null, "Notebook deleted successfully");
    }
}
