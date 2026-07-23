package com.lifeos.module.project.repository;

import com.lifeos.module.project.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for project.projects table.
 */
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {

    Page<ProjectEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Optional<ProjectEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
