package com.lifeos.module.audit.repository;

import com.lifeos.module.audit.entity.ActivityLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for audit.activity_logs table.
 */
@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLogEntity, UUID> {

    Page<ActivityLogEntity> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
