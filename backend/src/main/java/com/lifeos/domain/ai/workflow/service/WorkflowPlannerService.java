package com.lifeos.domain.ai.workflow.service;

import com.lifeos.domain.ai.workflow.dto.WorkflowExecutionPlanDTO;

import java.util.UUID;

/**
 * Service interface for Planning Workflows as DAG Graphs.
 */
public interface WorkflowPlannerService {

    WorkflowExecutionPlanDTO planWorkflow(UUID userId, String requestPrompt);
}
