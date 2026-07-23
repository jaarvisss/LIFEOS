package com.lifeos.module.ai.repository;

import com.lifeos.module.ai.entity.ReasoningLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for ai.reasoning_logs table.
 */
@Repository
public interface ReasoningLogRepository extends JpaRepository<ReasoningLogEntity, UUID> {

    Page<ReasoningLogEntity> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
