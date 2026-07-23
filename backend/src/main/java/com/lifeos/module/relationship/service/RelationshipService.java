package com.lifeos.module.relationship.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.relationship.dto.MilestoneRequest;
import com.lifeos.module.relationship.dto.MilestoneResponse;
import com.lifeos.module.relationship.dto.RelationshipRequest;
import com.lifeos.module.relationship.dto.RelationshipResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Relationship operations.
 */
public interface RelationshipService {

    RelationshipResponse createRelationship(UUID userId, RelationshipRequest request);

    RelationshipResponse getRelationshipById(UUID userId, UUID id);

    PagedResponse<RelationshipResponse> getAllRelationships(UUID userId, PaginationRequest paginationRequest);

    RelationshipResponse updateRelationship(UUID userId, UUID id, RelationshipRequest request);

    void deleteRelationship(UUID userId, UUID id);

    MilestoneResponse addMilestone(UUID userId, MilestoneRequest request);

    List<MilestoneResponse> getMilestones(UUID userId, UUID relationshipId);
}
