package com.lifeos.module.knowledge.repository;

import com.lifeos.module.knowledge.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for knowledge.notes table.
 */
@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {

    Page<NoteEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Page<NoteEntity> findByUserIdAndNotebookIdAndIsDeletedFalse(UUID userId, UUID notebookId, Pageable pageable);

    Page<NoteEntity> findByUserIdAndTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndIsDeletedFalse(
            UUID userId, String titleQuery, String contentQuery, Pageable pageable
    );

    Optional<NoteEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
