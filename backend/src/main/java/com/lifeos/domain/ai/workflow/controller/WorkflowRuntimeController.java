package com.lifeos.domain.ai.workflow.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.ai.workflow.dto.*;
import com.lifeos.domain.ai.workflow.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for LifeOS Workflow Engine & Agent Runtime.
 */
@RestController
@RequestMapping("/api/v1/workflows")
@RequiredArgsConstructor
@Tag(name = "Workflow & Agent Runtime Platform", description = "Endpoints for DAG workflow planning, execution, pause/resume, human approval, and agent runtime isolation")
public class WorkflowRuntimeController {

    private final WorkflowPlannerService plannerService;
    private final WorkflowExecutionEngine executionEngine;
    private final WorkflowApprovalService approvalService;
    private final AgentRuntimeService agentRuntimeService;

    @Data
    public static class WorkflowRequest {
        private String name;
        private String prompt;
    }

    @Data
    public static class ApprovalRequest {
        private UUID stepId;
        private boolean approved;
        private String rejectionReason;
    }

    @PostMapping
    @Operation(summary = "Create Workflow Definition", description = "Defines a new reusable workflow structure.")
    public ResponseEntity<ApiResponse<WorkflowExecutionPlanDTO>> createWorkflow(@RequestBody WorkflowRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(plannerService.planWorkflow(userId, request.getPrompt()), "Workflow created successfully");
    }

    @PostMapping("/execute")
    @Operation(summary = "Execute Workflow", description = "Triggers workflow instance execution over DAG graph.")
    public ResponseEntity<ApiResponse<WorkflowInstanceDTO>> executeWorkflow(@RequestBody WorkflowRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(executionEngine.executeWorkflow(userId, request.getName(), request.getPrompt()));
    }

    @PostMapping("/plan")
    @Operation(summary = "Plan Workflow Execution Graph", description = "Generates a DAG workflow plan from user prompt.")
    public ResponseEntity<ApiResponse<WorkflowExecutionPlanDTO>> planWorkflow(@RequestBody WorkflowRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(plannerService.planWorkflow(userId, request.getPrompt()));
    }

    @GetMapping
    @Operation(summary = "List User Workflows", description = "Retrieves workflow instances for active user.")
    public ResponseEntity<ApiResponse<List<WorkflowInstanceDTO>>> getUserWorkflows() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(executionEngine.getUserWorkflows(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Workflow Instance", description = "Retrieves workflow instance details by ID.")
    public ResponseEntity<ApiResponse<WorkflowInstanceDTO>> getWorkflowInstance(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(executionEngine.getWorkflowInstance(userId, id));
    }

    @PostMapping("/{id}/pause")
    @Operation(summary = "Pause Workflow Instance", description = "Pauses a running workflow instance.")
    public ResponseEntity<ApiResponse<WorkflowInstanceDTO>> pauseWorkflow(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(executionEngine.pauseWorkflow(userId, id));
    }

    @PostMapping("/{id}/resume")
    @Operation(summary = "Resume Workflow Instance", description = "Resumes a paused workflow instance.")
    public ResponseEntity<ApiResponse<WorkflowInstanceDTO>> resumeWorkflow(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(executionEngine.resumeWorkflow(userId, id));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel Workflow Instance", description = "Cancels a running or paused workflow instance.")
    public ResponseEntity<ApiResponse<WorkflowInstanceDTO>> cancelWorkflow(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(executionEngine.cancelWorkflow(userId, id));
    }

    @PostMapping("/{id}/retry")
    @Operation(summary = "Retry Workflow Instance", description = "Retries a failed workflow instance from checkpoint.")
    public ResponseEntity<ApiResponse<WorkflowInstanceDTO>> retryWorkflow(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(executionEngine.retryWorkflow(userId, id));
    }

    @GetMapping("/history")
    @Operation(summary = "Get Workflow Execution History", description = "Retrieves execution history across all instances.")
    public ResponseEntity<ApiResponse<List<WorkflowInstanceDTO>>> getHistory() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(executionEngine.getUserWorkflows(userId));
    }

    @GetMapping("/templates")
    @Operation(summary = "Get Workflow Templates", description = "Retrieves built-in workflow templates (Morning Routine, Evening Review, Meeting Prep).")
    public ResponseEntity<ApiResponse<List<String>>> getTemplates() {
        return ResponseBuilder.ok(List.of(
                "Morning Routine Workflow",
                "Evening Review Workflow",
                "Meeting Preparation Workflow",
                "Weekly Planning Workflow",
                "Monthly Budget Review Workflow"
        ));
    }

    @GetMapping("/agents")
    @Operation(summary = "Get Registered Agents", description = "Retrieves all registered AI Agents in the Agent Runtime.")
    public ResponseEntity<ApiResponse<List<AgentRegistrationDTO>>> getAgents() {
        return ResponseBuilder.ok(agentRuntimeService.getRegisteredAgents());
    }
}
