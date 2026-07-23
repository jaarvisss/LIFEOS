package com.lifeos.module.audit.repository;

import com.lifeos.module.audit.entity.AuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for audit.audit_logs table.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, UUID> {

    Page<AuditLogEntity> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    Page<AuditLogEntity> findByEntityNameAndEntityIdOrderByCreatedAtDesc(String entityName, UUID entityId, Pageable pageable);
}
