package com.lifeos.module.relationship.repository;

import com.lifeos.module.relationship.entity.RelationshipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for relationship.relationships table.
 */
@Repository
public interface RelationshipRepository extends JpaRepository<RelationshipEntity, UUID> {

    Page<RelationshipEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Optional<RelationshipEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);

    Optional<RelationshipEntity> findByUserIdAndContactIdAndIsDeletedFalse(UUID userId, UUID contactId);
}
