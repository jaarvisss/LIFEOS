package com.lifeos.module.document.repository;

import com.lifeos.module.document.entity.DocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for document.documents table.
 */
@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {

    Page<DocumentEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Optional<DocumentEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
