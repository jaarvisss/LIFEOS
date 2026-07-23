package com.lifeos.module.search.repository;

import com.lifeos.module.search.entity.SearchIndexEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for search.search_index table.
 */
@Repository
public interface SearchIndexRepository extends JpaRepository<SearchIndexEntity, UUID> {

    Page<SearchIndexEntity> findByUserIdAndTitleContainingIgnoreCaseOrContentSummaryContainingIgnoreCase(
            UUID userId, String titleQuery, String summaryQuery, Pageable pageable
    );

    Optional<SearchIndexEntity> findByUserIdAndEntityTypeAndEntityId(UUID userId, String entityType, UUID entityId);
}
