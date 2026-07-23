package com.lifeos.module.relationship.service;

import com.lifeos.common.exception.ConflictException;
import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.relationship.dto.MilestoneRequest;
import com.lifeos.module.relationship.dto.MilestoneResponse;
import com.lifeos.module.relationship.dto.RelationshipRequest;
import com.lifeos.module.relationship.dto.RelationshipResponse;
import com.lifeos.module.relationship.entity.MilestoneEntity;
import com.lifeos.module.relationship.entity.RelationshipEntity;
import com.lifeos.module.relationship.mapper.RelationshipMapper;
import com.lifeos.module.relationship.repository.MilestoneRepository;
import com.lifeos.module.relationship.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of RelationshipService interface.
 */
@Service
@RequiredArgsConstructor
public class RelationshipServiceImpl implements RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final MilestoneRepository milestoneRepository;
    private final RelationshipMapper relationshipMapper;

    @Override
    @Transactional
    public RelationshipResponse createRelationship(UUID userId, RelationshipRequest request) {
        if (relationshipRepository.findByUserIdAndContactIdAndIsDeletedFalse(userId, request.contactId()).isPresent()) {
            throw new ConflictException("Relationship record already exists for this contact");
        }

        RelationshipEntity entity = relationshipMapper.toEntity(request);
        entity.setUserId(userId);
        entity = relationshipRepository.save(entity);
        return relationshipMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public RelationshipResponse getRelationshipById(UUID userId, UUID id) {
        RelationshipEntity entity = relationshipRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", id));
        return relationshipMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<RelationshipResponse> getAllRelationships(UUID userId, PaginationRequest paginationRequest) {
        Page<RelationshipEntity> page = relationshipRepository.findByUserIdAndIsDeletedFalse(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(relationshipMapper::toDto));
    }

    @Override
    @Transactional
    public RelationshipResponse updateRelationship(UUID userId, UUID id, RelationshipRequest request) {
        RelationshipEntity entity = relationshipRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", id));

        relationshipMapper.updateEntityFromDto(request, entity);
        entity = relationshipRepository.save(entity);
        return relationshipMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteRelationship(UUID userId, UUID id) {
        RelationshipEntity entity = relationshipRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", id));
        entity.markDeleted();
        relationshipRepository.save(entity);
    }

    @Override
    @Transactional
    public MilestoneResponse addMilestone(UUID userId, MilestoneRequest request) {
        relationshipRepository.findByIdAndUserIdAndIsDeletedFalse(request.relationshipId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", request.relationshipId()));

        MilestoneEntity milestone = relationshipMapper.toEntity(request);
        milestone = milestoneRepository.save(milestone);
        return relationshipMapper.toDto(milestone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MilestoneResponse> getMilestones(UUID userId, UUID relationshipId) {
        relationshipRepository.findByIdAndUserIdAndIsDeletedFalse(relationshipId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", relationshipId));

        return relationshipMapper.toMilestoneDtoList(milestoneRepository.findByRelationshipId(relationshipId));
    }
}
