package com.lifeos.module.notification.repository;

import com.lifeos.module.notification.entity.NotificationChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for notification.notification_channels table.
 */
@Repository
public interface NotificationChannelRepository extends JpaRepository<NotificationChannelEntity, UUID> {

    List<NotificationChannelEntity> findByUserId(UUID userId);
}
