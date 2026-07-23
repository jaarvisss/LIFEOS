package com.lifeos.module.knowledge.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.knowledge.dto.NoteRequest;
import com.lifeos.module.knowledge.dto.NoteResponse;

import java.util.UUID;

/**
 * Service Interface for Note operations.
 */
public interface NoteService {

    NoteResponse createNote(UUID userId, NoteRequest request);

    NoteResponse getNoteById(UUID userId, UUID id);

    PagedResponse<NoteResponse> getAllNotes(UUID userId, UUID notebookId, PaginationRequest paginationRequest);

    PagedResponse<NoteResponse> searchNotes(UUID userId, String query, PaginationRequest paginationRequest);

    NoteResponse updateNote(UUID userId, UUID id, NoteRequest request);

    void deleteNote(UUID userId, UUID id);
}
