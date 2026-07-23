package com.lifeos.module.knowledge.service;

import com.lifeos.module.knowledge.dto.NotebookRequest;
import com.lifeos.module.knowledge.dto.NotebookResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Notebook operations.
 */
public interface NotebookService {

    NotebookResponse createNotebook(UUID userId, NotebookRequest request);

    NotebookResponse getNotebookById(UUID userId, UUID id);

    List<NotebookResponse> getNotebooksByUserId(UUID userId);

    NotebookResponse updateNotebook(UUID userId, UUID id, NotebookRequest request);

    void deleteNotebook(UUID userId, UUID id);
}
