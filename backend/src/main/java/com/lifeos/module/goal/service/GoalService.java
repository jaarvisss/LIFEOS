package com.lifeos.module.goal.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.goal.dto.GoalMilestoneRequest;
import com.lifeos.module.goal.dto.GoalMilestoneResponse;
import com.lifeos.module.goal.dto.GoalProgressRequest;
import com.lifeos.module.goal.dto.GoalRequest;
import com.lifeos.module.goal.dto.GoalResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Life Goals operations.
 */
public interface GoalService {

    GoalResponse createGoal(UUID userId, GoalRequest request);

    GoalResponse getGoalById(UUID userId, UUID id);

    PagedResponse<GoalResponse> getAllGoals(UUID userId, String status, PaginationRequest paginationRequest);

    GoalResponse updateGoal(UUID userId, UUID id, GoalRequest request);

    GoalResponse updateProgress(UUID userId, UUID id, GoalProgressRequest request);

    void deleteGoal(UUID userId, UUID id);

    GoalMilestoneResponse addMilestone(UUID userId, GoalMilestoneRequest request);

    List<GoalMilestoneResponse> getMilestones(UUID userId, UUID goalId);
}
