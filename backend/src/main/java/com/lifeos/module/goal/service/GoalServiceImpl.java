package com.lifeos.module.goal.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.goal.dto.GoalMilestoneRequest;
import com.lifeos.module.goal.dto.GoalMilestoneResponse;
import com.lifeos.module.goal.dto.GoalProgressRequest;
import com.lifeos.module.goal.dto.GoalRequest;
import com.lifeos.module.goal.dto.GoalResponse;
import com.lifeos.module.goal.entity.GoalEntity;
import com.lifeos.module.goal.entity.GoalMilestoneEntity;
import com.lifeos.module.goal.mapper.GoalMapper;
import com.lifeos.module.goal.repository.GoalMilestoneRepository;
import com.lifeos.module.goal.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of GoalService interface.
 */
@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final GoalMilestoneRepository goalMilestoneRepository;
    private final GoalMapper goalMapper;

    @Override
    @Transactional
    public GoalResponse createGoal(UUID userId, GoalRequest request) {
        GoalEntity goal = goalMapper.toEntity(request);
        goal.setUserId(userId);
        goal = goalRepository.save(goal);
        return goalMapper.toDto(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public GoalResponse getGoalById(UUID userId, UUID id) {
        GoalEntity goal = goalRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", id));
        return goalMapper.toDto(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<GoalResponse> getAllGoals(UUID userId, String status, PaginationRequest paginationRequest) {
        Page<GoalEntity> page;
        if (status != null && !status.isBlank()) {
            page = goalRepository.findByUserIdAndStatusAndIsDeletedFalse(userId, status, paginationRequest.toPageable());
        } else {
            page = goalRepository.findByUserIdAndIsDeletedFalse(userId, paginationRequest.toPageable());
        }
        return PagedResponse.from(page.map(goalMapper::toDto));
    }

    @Override
    @Transactional
    public GoalResponse updateGoal(UUID userId, UUID id, GoalRequest request) {
        GoalEntity goal = goalRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", id));

        goalMapper.updateEntityFromDto(request, goal);
        goal = goalRepository.save(goal);
        return goalMapper.toDto(goal);
    }

    @Override
    @Transactional
    public GoalResponse updateProgress(UUID userId, UUID id, GoalProgressRequest request) {
        GoalEntity goal = goalRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", id));

        goal.setProgressPercentage(request.progressPercentage());
        if (request.status() != null) {
            goal.setStatus(request.status());
        } else if (request.progressPercentage().compareTo(new BigDecimal("100.00")) >= 0) {
            goal.setStatus("ACHIEVED");
        }

        goal = goalRepository.save(goal);
        return goalMapper.toDto(goal);
    }

    @Override
    @Transactional
    public void deleteGoal(UUID userId, UUID id) {
        GoalEntity goal = goalRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", id));
        goal.markDeleted();
        goalRepository.save(goal);
    }

    @Override
    @Transactional
    public GoalMilestoneResponse addMilestone(UUID userId, GoalMilestoneRequest request) {
        goalRepository.findByIdAndUserIdAndIsDeletedFalse(request.goalId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", request.goalId()));

        GoalMilestoneEntity milestone = goalMapper.toEntity(request);
        if (Boolean.TRUE.equals(milestone.getIsCompleted())) {
            milestone.setCompletedAt(Instant.now());
        }
        milestone = goalMilestoneRepository.save(milestone);
        return goalMapper.toDto(milestone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalMilestoneResponse> getMilestones(UUID userId, UUID goalId) {
        goalRepository.findByIdAndUserIdAndIsDeletedFalse(goalId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", goalId));

        return goalMapper.toMilestoneDtoList(goalMilestoneRepository.findByGoalId(goalId));
    }
}
