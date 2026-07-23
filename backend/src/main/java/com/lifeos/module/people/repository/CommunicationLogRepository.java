package com.lifeos.module.people.repository;

import com.lifeos.module.people.entity.CommunicationLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for people.communication_logs table.
 */
@Repository
public interface CommunicationLogRepository extends JpaRepository<CommunicationLogEntity, UUID> {

    Page<CommunicationLogEntity> findByUserIdAndContactId(UUID userId, UUID contactId, Pageable pageable);

    Page<CommunicationLogEntity> findByUserId(UUID userId, Pageable pageable);
}
