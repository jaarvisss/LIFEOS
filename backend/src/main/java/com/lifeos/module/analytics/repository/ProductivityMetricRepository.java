package com.lifeos.module.analytics.repository;

import com.lifeos.module.analytics.entity.ProductivityMetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for analytics.productivity_metrics table.
 */
@Repository
public interface ProductivityMetricRepository extends JpaRepository<ProductivityMetricEntity, UUID> {

    Optional<ProductivityMetricEntity> findByUserIdAndMetricDate(UUID userId, LocalDate metricDate);

    List<ProductivityMetricEntity> findByUserIdAndMetricDateBetweenOrderByMetricDateAsc(UUID userId, LocalDate start, LocalDate end);
}
