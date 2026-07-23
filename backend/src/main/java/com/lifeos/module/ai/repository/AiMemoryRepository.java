package com.lifeos.module.ai.repository;

import com.lifeos.module.ai.entity.AiMemoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for ai.ai_memory table.
 */
@Repository
public interface AiMemoryRepository extends JpaRepository<AiMemoryEntity, UUID> {

    List<AiMemoryEntity> findByUserIdAndIsDeletedFalse(UUID userId);

    Page<AiMemoryEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Optional<AiMemoryEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
