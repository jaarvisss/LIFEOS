package com.lifeos.module.health.repository;

import com.lifeos.module.health.entity.HabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for health.habits table.
 */
@Repository
public interface HabitRepository extends JpaRepository<HabitEntity, UUID> {

    List<HabitEntity> findByUserIdAndIsDeletedFalse(UUID userId);

    Optional<HabitEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
