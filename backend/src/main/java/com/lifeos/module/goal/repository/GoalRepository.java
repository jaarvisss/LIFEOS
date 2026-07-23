package com.lifeos.module.goal.repository;

import com.lifeos.module.goal.entity.GoalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for goal.goals table.
 */
@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, UUID> {

    Page<GoalEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Page<GoalEntity> findByUserIdAndStatusAndIsDeletedFalse(UUID userId, String status, Pageable pageable);

    Optional<GoalEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
