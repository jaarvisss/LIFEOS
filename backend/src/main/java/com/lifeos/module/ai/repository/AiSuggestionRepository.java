package com.lifeos.module.ai.repository;

import com.lifeos.module.ai.entity.AiSuggestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for ai.ai_suggestions table.
 */
@Repository
public interface AiSuggestionRepository extends JpaRepository<AiSuggestionEntity, UUID> {

    List<AiSuggestionEntity> findByUserIdAndIsAcceptedIsNullAndIsDeletedFalseOrderByConfidenceScoreDesc(UUID userId);

    Page<AiSuggestionEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Optional<AiSuggestionEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
