package com.lifeos.module.knowledge.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.module.knowledge.dto.NotebookRequest;
import com.lifeos.module.knowledge.dto.NotebookResponse;
import com.lifeos.module.knowledge.entity.NotebookEntity;
import com.lifeos.module.knowledge.mapper.NotebookMapper;
import com.lifeos.module.knowledge.repository.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of NotebookService interface.
 */
@Service
@RequiredArgsConstructor
public class NotebookServiceImpl implements NotebookService {

    private final NotebookRepository notebookRepository;
    private final NotebookMapper notebookMapper;

    @Override
    @Transactional
    public NotebookResponse createNotebook(UUID userId, NotebookRequest request) {
        NotebookEntity entity = notebookMapper.toEntity(request);
        entity.setUserId(userId);
        entity = notebookRepository.save(entity);
        return notebookMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public NotebookResponse getNotebookById(UUID userId, UUID id) {
        NotebookEntity entity = notebookRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Notebook", "id", id));
        return notebookMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotebookResponse> getNotebooksByUserId(UUID userId) {
        return notebookMapper.toDtoList(notebookRepository.findByUserIdAndIsDeletedFalse(userId));
    }

    @Override
    @Transactional
    public NotebookResponse updateNotebook(UUID userId, UUID id, NotebookRequest request) {
        NotebookEntity entity = notebookRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Notebook", "id", id));

        notebookMapper.updateEntityFromDto(request, entity);
        entity = notebookRepository.save(entity);
        return notebookMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteNotebook(UUID userId, UUID id) {
        NotebookEntity entity = notebookRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Notebook", "id", id));
        entity.markDeleted();
        notebookRepository.save(entity);
    }
}
