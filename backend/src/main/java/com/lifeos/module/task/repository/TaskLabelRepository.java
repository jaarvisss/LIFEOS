package com.lifeos.module.task.repository;

import com.lifeos.module.task.entity.TaskLabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for task.task_labels table.
 */
@Repository
public interface TaskLabelRepository extends JpaRepository<TaskLabelEntity, UUID> {

    List<TaskLabelEntity> findByUserId(UUID userId);
}
