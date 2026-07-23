package com.lifeos.module.notification.repository;

import com.lifeos.module.notification.entity.DeliveryLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for notification.delivery_logs table.
 */
@Repository
public interface DeliveryLogRepository extends JpaRepository<DeliveryLogEntity, UUID> {

    Page<DeliveryLogEntity> findByUserId(UUID userId, Pageable pageable);
}
