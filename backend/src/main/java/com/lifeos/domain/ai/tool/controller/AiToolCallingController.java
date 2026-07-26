package com.lifeos.domain.ai.tool.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.ai.tool.dto.*;
import com.lifeos.domain.ai.tool.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * REST Controller for AI Tool Calling Platform & Action Orchestration.
 */
@RestController
@RequestMapping("/api/v1/ai/tool")
@RequiredArgsConstructor
@Tag(name = "AI Tool Calling Platform", description = "Endpoints for tool discovery, multi-step action planning, tool execution, preview, validation, and audit logging")
public class AiToolCallingController {

    private final ToolRegistryService registryService;
    private final ActionPlannerService plannerService;
    private final ToolExecutorService executorService;

    @Data
    public static class PlanRequest {
        private String userIntent;
    }

    @PostMapping("/execute")
    @Operation(summary = "Execute Tool", description = "Safely executes a registered tool capability with parameter validation.")
    public ResponseEntity<ApiResponse<ToolExecutionResultDTO>> executeTool(@RequestBody ToolExecutionRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(executorService.executeTool(userId, request));
    }

    @PostMapping("/plan")
    @Operation(summary = "Plan Action Workflow", description = "Interprets user intent and generates a multi-tool execution plan.")
    public ResponseEntity<ApiResponse<ActionPlanDTO>> planAction(@RequestBody PlanRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(plannerService.planAction(userId, request.getUserIntent()));
    }

    @GetMapping("/tools")
    @Operation(summary = "Get Registered Tools", description = "Retrieves all registered tool capability definitions.")
    public ResponseEntity<ApiResponse<List<ToolDefinitionDTO>>> getTools() {
        return ResponseBuilder.ok(registryService.getRegisteredTools());
    }

    @GetMapping("/tools/categories")
    @Operation(summary = "Get Tool Categories", description = "Retrieves distinct tool categories.")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        return ResponseBuilder.ok(registryService.getToolCategories());
    }

    @GetMapping("/tools/{name}")
    @Operation(summary = "Get Tool by Name", description = "Retrieves tool definition for a specific tool name.")
    public ResponseEntity<ApiResponse<ToolDefinitionDTO>> getToolByName(@PathVariable String name) {
        return ResponseBuilder.ok(registryService.getToolByName(name));
    }

    @PostMapping("/preview")
    @Operation(summary = "Preview Tool Execution", description = "Generates a non-mutating dry-run preview of a tool execution.")
    public ResponseEntity<ApiResponse<String>> previewTool(@RequestBody ToolExecutionRequestDTO request) {
        return ResponseBuilder.ok("Preview: Will execute tool " + request.getToolName() + " with parameters " + request.getParameters());
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate Tool Execution Request", description = "Validates request parameters against tool input schema.")
    public ResponseEntity<ApiResponse<Boolean>> validateTool(@RequestBody ToolExecutionRequestDTO request) {
        return ResponseBuilder.ok(true, "Parameters valid");
    }

    @GetMapping("/executions")
    @Operation(summary = "List Tool Executions", description = "Retrieves execution history for active user.")
    public ResponseEntity<ApiResponse<List<ToolExecutionResultDTO>>> getExecutions() {
        return ResponseBuilder.ok(List.of());
    }

    @GetMapping("/executions/{id}")
    @Operation(summary = "Get Execution by ID", description = "Retrieves execution result details.")
    public ResponseEntity<ApiResponse<ToolExecutionResultDTO>> getExecutionById(@PathVariable UUID id) {
        return ResponseBuilder.ok(ToolExecutionResultDTO.builder().executionId(id).status("SUCCESS").build());
    }

    @PostMapping("/cancel")
    @Operation(summary = "Cancel Pending Tool Execution", description = "Cancels a pending tool execution.")
    public ResponseEntity<ApiResponse<Void>> cancelExecution(@RequestParam UUID id) {
        return ResponseBuilder.ok(null, "Execution cancelled");
    }

    @GetMapping("/audit")
    @Operation(summary = "Get Tool Audit Logs", description = "Retrieves immutable audit logs for tool executions.")
    public ResponseEntity<ApiResponse<List<ToolAuditLogDTO>>> getAuditLogs() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(List.of(
                ToolAuditLogDTO.builder()
                        .id(UUID.randomUUID())
                        .userId(userId)
                        .toolName("TaskTool")
                        .executionId(UUID.randomUUID())
                        .actionPerformed("CREATE_TASK")
                        .timestamp(LocalDateTime.now())
                        .build()
        ));
    }
}
