package com.lifeos.module.relationship.repository;

import com.lifeos.module.relationship.entity.MilestoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for relationship.milestones table.
 */
@Repository
public interface MilestoneRepository extends JpaRepository<MilestoneEntity, UUID> {

    List<MilestoneEntity> findByRelationshipId(UUID relationshipId);
}
