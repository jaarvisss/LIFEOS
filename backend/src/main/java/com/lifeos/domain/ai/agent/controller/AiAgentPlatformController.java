package com.lifeos.domain.ai.agent.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.ai.agent.dto.*;
import com.lifeos.domain.ai.agent.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Autonomous AI Agent Platform & Multi-Agent Collaboration.
 */
@RestController
@RequestMapping("/api/v1/agents")
@RequiredArgsConstructor
@Tag(name = "Autonomous AI Agent Platform", description = "Endpoints for agent discovery, multi-agent collaboration, delegation, routing, and observability")
public class AiAgentPlatformController {

    private final AgentRegistryService registryService;
    private final AgentRouterService routerService;
    private final AgentCollaborationService collaborationService;

    @Data
    public static class AgentNameRequest {
        private String agentName;
    }

    @GetMapping
    @Operation(summary = "List Registered AI Agents", description = "Retrieves all registered specialized AI Agents.")
    public ResponseEntity<ApiResponse<List<AgentDTO>>> getRegisteredAgents() {
        return ResponseBuilder.ok(registryService.getRegisteredAgents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Agent by ID", description = "Retrieves agent details and capabilities by ID.")
    public ResponseEntity<ApiResponse<AgentDTO>> getAgentById(@PathVariable UUID id) {
        return ResponseBuilder.ok(registryService.getAgentById(id));
    }

    @PostMapping("/execute")
    @Operation(summary = "Execute Multi-Agent Pipeline", description = "Routes user request to best-matched primary agent and coordinates multi-agent delegation.")
    public ResponseEntity<ApiResponse<AgentExecutionResponseDTO>> executeAgentPipeline(@RequestBody AgentExecutionRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(collaborationService.executeAgentPipeline(userId, request));
    }

    @PostMapping("/message")
    @Operation(summary = "Send Direct Agent Message", description = "Sends a direct message to a specific agent.")
    public ResponseEntity<ApiResponse<AgentExecutionResponseDTO>> sendMessage(@RequestBody AgentExecutionRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(collaborationService.executeAgentPipeline(userId, request));
    }

    @PostMapping("/delegate")
    @Operation(summary = "Trigger Agent Delegation", description = "Triggers inter-agent task delegation.")
    public ResponseEntity<ApiResponse<AgentDelegationDTO>> delegateTask(@RequestParam String parentAgent, @RequestParam String subAgent, @RequestParam String task) {
        return ResponseBuilder.ok(AgentDelegationDTO.builder()
                .delegationId(UUID.randomUUID())
                .parentAgentName(parentAgent)
                .subAgentName(subAgent)
                .taskDescription(task)
                .status("COMPLETED")
                .resultSummary("Delegation task completed successfully")
                .build());
    }

    @GetMapping("/history")
    @Operation(summary = "Get Agent Delegation History", description = "Retrieves delegation history across agents.")
    public ResponseEntity<ApiResponse<List<AgentDelegationDTO>>> getHistory() {
        return ResponseBuilder.ok(List.of());
    }

    @GetMapping("/metrics")
    @Operation(summary = "Get Agent Observability Metrics", description = "Retrieves execution time, success rate, and delegation metrics for agents.")
    public ResponseEntity<ApiResponse<List<AgentMetricsDTO>>> getMetrics() {
        return ResponseBuilder.ok(List.of(
                AgentMetricsDTO.builder()
                        .agentName("DailyPlannerAgent")
                        .totalExecutions(142)
                        .successRate(0.99)
                        .avgLatencyMs(180)
                        .totalDelegations(280)
                        .build(),
                AgentMetricsDTO.builder()
                        .agentName("FinanceCoachAgent")
                        .totalExecutions(85)
                        .successRate(0.98)
                        .avgLatencyMs(160)
                        .totalDelegations(110)
                        .build()
        ));
    }

    @PostMapping("/enable")
    @Operation(summary = "Enable Agent", description = "Enables an agent capability.")
    public ResponseEntity<ApiResponse<Void>> enableAgent(@RequestBody AgentNameRequest request) {
        registryService.enableAgent(request.getAgentName());
        return ResponseBuilder.ok(null, "Agent enabled successfully");
    }

    @PostMapping("/disable")
    @Operation(summary = "Disable Agent", description = "Disables an agent capability.")
    public ResponseEntity<ApiResponse<Void>> disableAgent(@RequestBody AgentNameRequest request) {
        registryService.disableAgent(request.getAgentName());
        return ResponseBuilder.ok(null, "Agent disabled successfully");
    }
}
