package com.lifeos.domain.ai.workflow.service;

import com.lifeos.domain.ai.workflow.dto.WorkflowInstanceDTO;

import java.util.UUID;

/**
 * Service interface for Human-in-the-loop Approvals.
 */
public interface WorkflowApprovalService {

    WorkflowInstanceDTO approveWorkflowStep(UUID userId, UUID instanceId, UUID stepId);

    WorkflowInstanceDTO rejectWorkflowStep(UUID userId, UUID instanceId, UUID stepId, String reason);
}
