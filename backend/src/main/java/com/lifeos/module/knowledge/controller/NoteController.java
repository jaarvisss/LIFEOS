package com.lifeos.module.knowledge.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.knowledge.dto.NoteRequest;
import com.lifeos.module.knowledge.dto.NoteResponse;
import com.lifeos.module.knowledge.service.NoteService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller for Knowledge Base Notes.
 */
@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
@Tag(name = "Knowledge Base Notes", description = "Endpoints for creating, managing, and searching personal notes")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @Operation(summary = "Create Note", description = "Creates a new Markdown note.")
    public ResponseEntity<ApiResponse<NoteResponse>> createNote(@Valid @RequestBody NoteRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(noteService.createNote(userId, request), "Note created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Note by ID", description = "Retrieves note details.")
    public ResponseEntity<ApiResponse<NoteResponse>> getNoteById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(noteService.getNoteById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Notes", description = "Retrieves paginated list of notes.")
    public ResponseEntity<ApiResponse<PagedResponse<NoteResponse>>> getAllNotes(
            @RequestParam(value = "notebookId", required = false) UUID notebookId,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(noteService.getAllNotes(userId, notebookId, paginationRequest));
    }

    @GetMapping("/search")
    @Operation(summary = "Search Notes", description = "Performs full-text keyword search on note title and content.")
    public ResponseEntity<ApiResponse<PagedResponse<NoteResponse>>> searchNotes(
            @RequestParam("q") String query,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(noteService.searchNotes(userId, query, paginationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Note", description = "Updates note content.")
    public ResponseEntity<ApiResponse<NoteResponse>> updateNote(
            @PathVariable UUID id,
            @Valid @RequestBody NoteRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(noteService.updateNote(userId, id, request), "Note updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Note", description = "Soft-deletes a note.")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        noteService.deleteNote(userId, id);
        return ResponseBuilder.ok(null, "Note deleted successfully");
    }
}
