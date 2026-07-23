package com.lifeos.module.analytics.repository;

import com.lifeos.module.analytics.entity.LifeScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for analytics.life_scores table.
 */
@Repository
public interface LifeScoreRepository extends JpaRepository<LifeScoreEntity, UUID> {

    Optional<LifeScoreEntity> findByUserIdAndScoreDate(UUID userId, LocalDate scoreDate);

    List<LifeScoreEntity> findByUserIdOrderByScoreDateDesc(UUID userId);
}
