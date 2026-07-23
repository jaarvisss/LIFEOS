package com.lifeos.module.knowledge.repository;

import com.lifeos.module.knowledge.entity.NotebookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for knowledge.notebooks table.
 */
@Repository
public interface NotebookRepository extends JpaRepository<NotebookEntity, UUID> {

    List<NotebookEntity> findByUserIdAndIsDeletedFalse(UUID userId);

    Optional<NotebookEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
