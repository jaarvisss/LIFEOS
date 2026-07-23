package com.lifeos.module.health.repository;

import com.lifeos.module.health.entity.SleepLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for health.sleep_logs table.
 */
@Repository
public interface SleepLogRepository extends JpaRepository<SleepLogEntity, UUID> {

    Page<SleepLogEntity> findByUserId(UUID userId, Pageable pageable);
}
