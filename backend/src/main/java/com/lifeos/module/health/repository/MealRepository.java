package com.lifeos.module.health.repository;

import com.lifeos.module.health.entity.MealEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for health.meals table.
 */
@Repository
public interface MealRepository extends JpaRepository<MealEntity, UUID> {

    Page<MealEntity> findByUserId(UUID userId, Pageable pageable);
}
