package com.lifeos.domain.ai.tool.service;

import com.lifeos.domain.ai.tool.dto.*;
import com.lifeos.domain.ai.tool.tools.ExecutableTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Unified Implementation for AI Tool Calling Platform, Action Planning, Tool Execution, and Security Validation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ToolCallingServiceImpl implements
        ToolRegistryService,
        ActionPlannerService,
        ToolExecutorService,
        ToolSecurityService {

    private final List<ExecutableTool> toolList;

    @Override
    public List<ToolDefinitionDTO> getRegisteredTools() {
        return toolList.stream()
                .map(t -> ToolDefinitionDTO.builder()
                        .toolName(t.getToolName())
                        .description(t.getDescription())
                        .category(t.getCategory())
                        .version("1.0.0")
                        .enabled(true)
                        .requiresConfirmation(t.requiresConfirmation())
                        .inputSchema("{\"type\": \"object\"}")
                        .outputSchema("{\"type\": \"object\"}")
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getToolCategories() {
        return toolList.stream()
                .map(ExecutableTool::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public ToolDefinitionDTO getToolByName(String name) {
        return toolList.stream()
                .filter(t -> t.getToolName().equalsIgnoreCase(name))
                .findFirst()
                .map(t -> ToolDefinitionDTO.builder()
                        .toolName(t.getToolName())
                        .description(t.getDescription())
                        .category(t.getCategory())
                        .version("1.0.0")
                        .enabled(true)
                        .requiresConfirmation(t.requiresConfirmation())
                        .build())
                .orElse(null);
    }

    @Override
    public ActionPlanDTO planAction(UUID userId, String userIntent) {
        String intentLower = userIntent.toLowerCase();
        List<ActionStepDTO> steps = new ArrayList<>();

        if (intentLower.contains("task") || intentLower.contains("call")) {
            steps.add(ActionStepDTO.builder()
                    .stepNumber(1)
                    .toolName("TaskTool")
                    .stepDescription("Create task entry")
                    .parameters(Map.of("title", userIntent, "priority", "HIGH"))
                    .requiresConfirmation(false)
                    .build());
        }

        if (intentLower.contains("meeting") || intentLower.contains("schedule") || intentLower.contains("tomorrow")) {
            steps.add(ActionStepDTO.builder()
                    .stepNumber(steps.size() + 1)
                    .toolName("CalendarTool")
                    .stepDescription("Schedule calendar event")
                    .parameters(Map.of("title", userIntent, "time", "05:00 PM"))
                    .requiresConfirmation(false)
                    .build());
        }

        if (intentLower.contains("expense") || intentLower.contains("budget") || intentLower.contains("spent") || intentLower.contains("₹") || intentLower.contains("$")) {
            steps.add(ActionStepDTO.builder()
                    .stepNumber(steps.size() + 1)
                    .toolName("FinanceTool")
                    .stepDescription("Log financial transaction")
                    .parameters(Map.of("amount", 500, "category", "Grocery", "type", "EXPENSE"))
                    .requiresConfirmation(false)
                    .build());
        }

        if (steps.isEmpty()) {
            steps.add(ActionStepDTO.builder()
                    .stepNumber(1)
                    .toolName("KnowledgeTool")
                    .stepDescription("Capture note in Knowledge Base")
                    .parameters(Map.of("title", "Quick Note", "content", userIntent))
                    .requiresConfirmation(false)
                    .build());
        }

        return ActionPlanDTO.builder()
                .goal("Execute action for: " + userIntent)
                .steps(steps)
                .confidenceScore(0.96)
                .estimatedTotalTimeMs(120)
                .build();
    }

    @Override
    public ToolExecutionResultDTO executeTool(UUID userId, ToolExecutionRequestDTO request) {
        if (!authorizeExecution(userId, request)) {
            return ToolExecutionResultDTO.builder()
                    .executionId(UUID.randomUUID())
                    .toolName(request.getToolName())
                    .status("FAILED")
                    .errorMessage("Unauthorized or unconfirmed tool execution request")
                    .build();
        }

        ExecutableTool tool = toolList.stream()
                .filter(t -> t.getToolName().equalsIgnoreCase(request.getToolName()))
                .findFirst()
                .orElse(null);

        if (tool == null) {
            return ToolExecutionResultDTO.builder()
                    .executionId(UUID.randomUUID())
                    .toolName(request.getToolName())
                    .status("FAILED")
                    .errorMessage("Tool not found in Tool Registry: " + request.getToolName())
                    .build();
        }

        return tool.execute(userId, request);
    }

    @Override
    public boolean authorizeExecution(UUID userId, ToolExecutionRequestDTO request) {
        ExecutableTool tool = toolList.stream()
                .filter(t -> t.getToolName().equalsIgnoreCase(request.getToolName()))
                .findFirst()
                .orElse(null);

        if (tool != null && tool.requiresConfirmation() && !request.isConfirmExecution()) {
            log.warn("Tool {} requires explicit user confirmation before execution", tool.getToolName());
            return false;
        }
        return true;
    }
}
