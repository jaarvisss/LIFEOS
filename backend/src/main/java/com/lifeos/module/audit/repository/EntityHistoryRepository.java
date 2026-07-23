package com.lifeos.module.audit.repository;

import com.lifeos.module.audit.entity.EntityHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for audit.entity_history table.
 */
@Repository
public interface EntityHistoryRepository extends JpaRepository<EntityHistoryEntity, UUID> {

    List<EntityHistoryEntity> findByEntityNameAndEntityIdOrderByVersionNumberDesc(String entityName, UUID entityId);
}
