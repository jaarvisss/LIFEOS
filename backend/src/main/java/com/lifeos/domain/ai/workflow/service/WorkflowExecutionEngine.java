package com.lifeos.domain.ai.workflow.service;

import com.lifeos.domain.ai.workflow.dto.WorkflowInstanceDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Workflow Execution Engine.
 */
public interface WorkflowExecutionEngine {

    WorkflowInstanceDTO executeWorkflow(UUID userId, String workflowName, String requestPrompt);

    WorkflowInstanceDTO getWorkflowInstance(UUID userId, UUID instanceId);

    List<WorkflowInstanceDTO> getUserWorkflows(UUID userId);

    WorkflowInstanceDTO pauseWorkflow(UUID userId, UUID instanceId);

    WorkflowInstanceDTO resumeWorkflow(UUID userId, UUID instanceId);

    WorkflowInstanceDTO cancelWorkflow(UUID userId, UUID instanceId);

    WorkflowInstanceDTO retryWorkflow(UUID userId, UUID instanceId);
}
