package com.lifeos.module.health.repository;

import com.lifeos.module.health.entity.HabitLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for health.habit_logs table.
 */
@Repository
public interface HabitLogRepository extends JpaRepository<HabitLogEntity, UUID> {

    List<HabitLogEntity> findByHabitIdOrderByCompletedDateDesc(UUID habitId);

    Optional<HabitLogEntity> findByHabitIdAndCompletedDate(UUID habitId, LocalDate completedDate);
}
