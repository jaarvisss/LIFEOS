package com.lifeos.module.profile.repository;

import com.lifeos.module.profile.entity.WorkLifeBoundariesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for profile.work_life_boundaries table.
 */
@Repository
public interface WorkLifeBoundariesRepository extends JpaRepository<WorkLifeBoundariesEntity, UUID> {

    Optional<WorkLifeBoundariesEntity> findByUserId(UUID userId);
}
