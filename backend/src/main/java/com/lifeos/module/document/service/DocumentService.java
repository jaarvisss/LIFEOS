package com.lifeos.module.document.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.document.dto.DocumentRequest;
import com.lifeos.module.document.dto.DocumentResponse;
import com.lifeos.module.document.dto.DocumentVersionResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Document operations.
 */
public interface DocumentService {

    DocumentResponse createDocument(UUID userId, DocumentRequest request);

    DocumentResponse getDocumentById(UUID userId, UUID id);

    PagedResponse<DocumentResponse> getAllDocuments(UUID userId, PaginationRequest paginationRequest);

    DocumentResponse updateDocument(UUID userId, UUID id, DocumentRequest request);

    void deleteDocument(UUID userId, UUID id);

    List<DocumentVersionResponse> getDocumentVersions(UUID userId, UUID documentId);
}
