package com.lifeos.module.task.repository;

import com.lifeos.module.task.entity.SubtaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for task.subtasks table.
 */
@Repository
public interface SubtaskRepository extends JpaRepository<SubtaskEntity, UUID> {

    List<SubtaskEntity> findByTaskIdOrderBySequenceOrderAsc(UUID taskId);
}
