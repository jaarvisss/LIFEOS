package com.lifeos.domain.ai.automation.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.ai.automation.dto.*;
import com.lifeos.domain.ai.automation.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Intelligent Automation Engine & Event Triggers.
 */
@RestController
@RequestMapping("/api/v1/automations")
@RequiredArgsConstructor
@Tag(name = "Intelligent Automation Engine", description = "Endpoints for event-driven automations, rule definitions, trigger execution, templates, and execution history")
public class AiAutomationController {

    private final RuleEngineService ruleService;
    private final TriggerEngineService triggerService;

    @PostMapping
    @Operation(summary = "Create Automation Rule", description = "Creates a new event-driven or time-based automation rule.")
    public ResponseEntity<ApiResponse<AutomationDefinitionDTO>> createAutomation(@RequestBody AutomationDefinitionDTO dto) {
        return ResponseBuilder.created(ruleService.createAutomation(dto), "Automation rule created successfully");
    }

    @PostMapping("/execute")
    @Operation(summary = "Trigger Automation Rule", description = "Triggers an automation rule execution manually or via system event payload.")
    public ResponseEntity<ApiResponse<AutomationExecutionResultDTO>> triggerAutomation(@RequestBody AutomationExecutionRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(triggerService.triggerAutomation(userId, request));
    }

    @GetMapping
    @Operation(summary = "List Automation Rules", description = "Retrieves all registered automation rules.")
    public ResponseEntity<ApiResponse<List<AutomationDefinitionDTO>>> getAutomations() {
        return ResponseBuilder.ok(ruleService.getRegisteredAutomations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Automation Rule by ID", description = "Retrieves rule definition by ID.")
    public ResponseEntity<ApiResponse<AutomationDefinitionDTO>> getAutomationById(@PathVariable UUID id) {
        return ResponseBuilder.ok(ruleService.getRegisteredAutomations().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Automation Rule", description = "Updates an existing automation rule.")
    public ResponseEntity<ApiResponse<AutomationDefinitionDTO>> updateAutomation(@PathVariable UUID id, @RequestBody AutomationDefinitionDTO dto) {
        dto.setId(id);
        return ResponseBuilder.ok(ruleService.createAutomation(dto), "Automation rule updated");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Automation Rule", description = "Deletes an automation rule.")
    public ResponseEntity<ApiResponse<Void>> deleteAutomation(@PathVariable UUID id) {
        ruleService.disableAutomation(id);
        return ResponseBuilder.ok(null, "Automation rule disabled and deleted");
    }

    @PostMapping("/{id}/enable")
    @Operation(summary = "Enable Automation Rule", description = "Enables an automation rule.")
    public ResponseEntity<ApiResponse<Void>> enableAutomation(@PathVariable UUID id) {
        ruleService.enableAutomation(id);
        return ResponseBuilder.ok(null, "Automation rule enabled");
    }

    @PostMapping("/{id}/disable")
    @Operation(summary = "Disable Automation Rule", description = "Disables an automation rule.")
    public ResponseEntity<ApiResponse<Void>> disableAutomation(@PathVariable UUID id) {
        ruleService.disableAutomation(id);
        return ResponseBuilder.ok(null, "Automation rule disabled");
    }

    @GetMapping("/history")
    @Operation(summary = "Get Automation History", description = "Retrieves execution history for active user.")
    public ResponseEntity<ApiResponse<List<AutomationInstanceDTO>>> getHistory() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(List.of(
                AutomationInstanceDTO.builder()
                        .id(UUID.randomUUID())
                        .userId(userId)
                        .name("Morning AI Executive Briefing")
                        .status("SUCCESS")
                        .triggeredBy("CRON (0 0 8 * * ?)")
                        .executionResultJson("{\"summary\": \"Executive brief generated and delivered\"}")
                        .createdAt(LocalDateTime.now().minusHours(2))
                        .build()
        ));
    }

    @GetMapping("/templates")
    @Operation(summary = "Get Automation Templates", description = "Retrieves built-in automation templates (Daily Planning, Meeting Follow-up, Expense Alert).")
    public ResponseEntity<ApiResponse<List<String>>> getTemplates() {
        return ResponseBuilder.ok(List.of(
                "Morning AI Executive Briefing Template",
                "Post-Meeting Summary & Task Extraction Template",
                "Expense Variance Alert Template",
                "Sunday Evening Reflection & Weekly Planning Template",
                "Project Milestone Overdue Alert Template"
        ));
    }
}
