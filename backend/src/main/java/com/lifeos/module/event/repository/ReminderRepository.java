package com.lifeos.module.event.repository;

import com.lifeos.module.event.entity.ReminderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for event.reminders table.
 */
@Repository
public interface ReminderRepository extends JpaRepository<ReminderEntity, UUID> {

    List<ReminderEntity> findByEventId(UUID eventId);
}
