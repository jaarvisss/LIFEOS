package com.lifeos.module.analytics.repository;

import com.lifeos.module.analytics.entity.DashboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for analytics.dashboards table.
 */
@Repository
public interface DashboardRepository extends JpaRepository<DashboardEntity, UUID> {

    List<DashboardEntity> findByUserId(UUID userId);

    Optional<DashboardEntity> findByUserIdAndIsDefaultTrue(UUID userId);
}
