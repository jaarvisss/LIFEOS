package com.lifeos.module.health.repository;

import com.lifeos.module.health.entity.WorkoutEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for health.workouts table.
 */
@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutEntity, UUID> {

    Page<WorkoutEntity> findByUserId(UUID userId, Pageable pageable);
}
