package com.lifeos.module.knowledge.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.knowledge.dto.NoteRequest;
import com.lifeos.module.knowledge.dto.NoteResponse;
import com.lifeos.module.knowledge.entity.NoteEntity;
import com.lifeos.module.knowledge.mapper.NoteMapper;
import com.lifeos.module.knowledge.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of NoteService interface.
 */
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    @Transactional
    public NoteResponse createNote(UUID userId, NoteRequest request) {
        NoteEntity note = noteMapper.toEntity(request);
        note.setUserId(userId);
        if (note.getIsFavorite() == null) {
            note.setIsFavorite(false);
        }
        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    @Override
    @Transactional(readOnly = true)
    public NoteResponse getNoteById(UUID userId, UUID id) {
        NoteEntity note = noteRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
        return noteMapper.toDto(note);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<NoteResponse> getAllNotes(UUID userId, UUID notebookId, PaginationRequest paginationRequest) {
        Page<NoteEntity> page;
        if (notebookId != null) {
            page = noteRepository.findByUserIdAndNotebookIdAndIsDeletedFalse(userId, notebookId, paginationRequest.toPageable());
        } else {
            page = noteRepository.findByUserIdAndIsDeletedFalse(userId, paginationRequest.toPageable());
        }
        return PagedResponse.from(page.map(noteMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<NoteResponse> searchNotes(UUID userId, String query, PaginationRequest paginationRequest) {
        Page<NoteEntity> page = noteRepository.findByUserIdAndTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndIsDeletedFalse(
                userId, query, query, paginationRequest.toPageable()
        );
        return PagedResponse.from(page.map(noteMapper::toDto));
    }

    @Override
    @Transactional
    public NoteResponse updateNote(UUID userId, UUID id, NoteRequest request) {
        NoteEntity note = noteRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

        noteMapper.updateEntityFromDto(request, note);
        if (note.getIsFavorite() == null) {
            note.setIsFavorite(false);
        }
        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    @Override
    @Transactional
    public void deleteNote(UUID userId, UUID id) {
        NoteEntity note = noteRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
        note.markDeleted();
        noteRepository.save(note);
    }
}
