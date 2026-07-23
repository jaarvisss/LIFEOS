package com.lifeos.module.ai.repository;

import com.lifeos.module.ai.entity.AiTaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for ai.ai_tasks table.
 */
@Repository
public interface AiTaskRepository extends JpaRepository<AiTaskEntity, UUID> {

    Page<AiTaskEntity> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
