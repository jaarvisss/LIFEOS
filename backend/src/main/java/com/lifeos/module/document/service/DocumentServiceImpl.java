package com.lifeos.module.document.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.document.dto.DocumentRequest;
import com.lifeos.module.document.dto.DocumentResponse;
import com.lifeos.module.document.dto.DocumentVersionResponse;
import com.lifeos.module.document.entity.DocumentEntity;
import com.lifeos.module.document.entity.DocumentVersionEntity;
import com.lifeos.module.document.mapper.DocumentMapper;
import com.lifeos.module.document.repository.DocumentRepository;
import com.lifeos.module.document.repository.DocumentVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of DocumentService interface.
 */
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final DocumentMapper documentMapper;

    @Override
    @Transactional
    public DocumentResponse createDocument(UUID userId, DocumentRequest request) {
        DocumentEntity entity = documentMapper.toEntity(request);
        entity.setUserId(userId);
        entity = documentRepository.save(entity);

        DocumentVersionEntity version = DocumentVersionEntity.builder()
                .documentId(entity.getId())
                .versionNumber(1)
                .fileSizeBytes(request.fileSizeBytes() != null ? request.fileSizeBytes() : 0L)
                .checksumSha256(request.checksumSha256() != null ? request.checksumSha256() : "SHA256-INIT")
                .storageKey(request.storageKey())
                .build();
        documentVersionRepository.save(version);

        return documentMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponse getDocumentById(UUID userId, UUID id) {
        DocumentEntity entity = documentRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", id));
        return documentMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<DocumentResponse> getAllDocuments(UUID userId, PaginationRequest paginationRequest) {
        Page<DocumentEntity> page = documentRepository.findByUserIdAndIsDeletedFalse(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(documentMapper::toDto));
    }

    @Override
    @Transactional
    public DocumentResponse updateDocument(UUID userId, UUID id, DocumentRequest request) {
        DocumentEntity entity = documentRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", id));

        documentMapper.updateEntityFromDto(request, entity);
        entity = documentRepository.save(entity);
        return documentMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteDocument(UUID userId, UUID id) {
        DocumentEntity entity = documentRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", id));
        entity.markDeleted();
        documentRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentVersionResponse> getDocumentVersions(UUID userId, UUID documentId) {
        documentRepository.findByIdAndUserIdAndIsDeletedFalse(documentId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", documentId));

        return documentMapper.toVersionDtoList(documentVersionRepository.findByDocumentIdOrderByVersionNumberDesc(documentId));
    }
}
