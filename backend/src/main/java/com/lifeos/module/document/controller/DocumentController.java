package com.lifeos.module.document.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.document.dto.DocumentRequest;
import com.lifeos.module.document.dto.DocumentResponse;
import com.lifeos.module.document.dto.DocumentVersionResponse;
import com.lifeos.module.document.service.DocumentService;
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
 * REST Controller for Document Management & Versioning.
 */
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
@Tag(name = "Document Management", description = "Endpoints for document metadata, version history, and file storage links")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    @Operation(summary = "Create Document", description = "Registers document metadata and storage key.")
    public ResponseEntity<ApiResponse<DocumentResponse>> createDocument(@Valid @RequestBody DocumentRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(documentService.createDocument(userId, request), "Document created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Document by ID", description = "Retrieves document metadata.")
    public ResponseEntity<ApiResponse<DocumentResponse>> getDocumentById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(documentService.getDocumentById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Documents", description = "Retrieves paginated list of user documents.")
    public ResponseEntity<ApiResponse<PagedResponse<DocumentResponse>>> getAllDocuments(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(documentService.getAllDocuments(userId, paginationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Document", description = "Updates document metadata.")
    public ResponseEntity<ApiResponse<DocumentResponse>> updateDocument(
            @PathVariable UUID id,
            @Valid @RequestBody DocumentRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(documentService.updateDocument(userId, id, request), "Document updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Document", description = "Soft-deletes a document record.")
    public ResponseEntity<ApiResponse<Void>> deleteDocument(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        documentService.deleteDocument(userId, id);
        return ResponseBuilder.ok(null, "Document deleted successfully");
    }

    @GetMapping("/{id}/versions")
    @Operation(summary = "Get Document Versions", description = "Retrieves revision history for a document.")
    public ResponseEntity<ApiResponse<List<DocumentVersionResponse>>> getDocumentVersions(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(documentService.getDocumentVersions(userId, id));
    }
}
