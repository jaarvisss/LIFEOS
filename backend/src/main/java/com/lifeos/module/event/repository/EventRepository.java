package com.lifeos.module.event.repository;

import com.lifeos.module.event.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for event.events table.
 */
@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {

    Page<EventEntity> findByCalendarIdAndIsDeletedFalse(UUID calendarId, Pageable pageable);

    @Query("SELECT e FROM EventEntity e WHERE e.calendarId = :calendarId AND e.isDeleted = false AND e.startTime >= :start AND e.endTime <= :end")
    List<EventEntity> findEventsInTimeRange(UUID calendarId, Instant start, Instant end);

    Optional<EventEntity> findByIdAndIsDeletedFalse(UUID id);
}
