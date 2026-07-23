package com.lifeos.module.task.repository;

import com.lifeos.module.task.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for task.tasks table.
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    Page<TaskEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Page<TaskEntity> findByUserIdAndStatusAndIsDeletedFalse(UUID userId, String status, Pageable pageable);

    Page<TaskEntity> findByUserIdAndProjectIdAndIsDeletedFalse(UUID userId, UUID projectId, Pageable pageable);

    Optional<TaskEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
