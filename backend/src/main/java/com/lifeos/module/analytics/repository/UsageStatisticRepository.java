package com.lifeos.module.analytics.repository;

import com.lifeos.module.analytics.entity.UsageStatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for analytics.usage_statistics table.
 */
@Repository
public interface UsageStatisticRepository extends JpaRepository<UsageStatisticEntity, UUID> {

    Optional<UsageStatisticEntity> findByUserIdAndFeatureName(UUID userId, String featureName);

    List<UsageStatisticEntity> findByUserIdOrderByLastUsedAtDesc(UUID userId);
}
