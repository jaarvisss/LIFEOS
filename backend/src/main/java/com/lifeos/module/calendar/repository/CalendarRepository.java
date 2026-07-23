package com.lifeos.module.calendar.repository;

import com.lifeos.module.calendar.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for calendar.calendars table.
 */
@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, UUID> {

    List<CalendarEntity> findByUserIdAndIsDeletedFalse(UUID userId);

    Optional<CalendarEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
