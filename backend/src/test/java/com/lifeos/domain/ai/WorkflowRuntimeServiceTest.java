package com.lifeos.domain.ai;

import com.lifeos.domain.ai.tool.dto.ActionPlanDTO;
import com.lifeos.domain.ai.tool.dto.ActionStepDTO;
import com.lifeos.domain.ai.tool.service.ActionPlannerService;

import com.lifeos.domain.ai.workflow.dto.*;
import com.lifeos.domain.ai.workflow.service.WorkflowRuntimeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Unit Test Suite for LifeOS Workflow Engine & Agent Runtime.
 */
class WorkflowRuntimeServiceTest {

    @Test
    @DisplayName("Should generate DAG workflow execution plan from user request")
    void shouldPlanWorkflow() {
        ActionPlannerService mockActionPlanner = Mockito.mock(ActionPlannerService.class);
        UUID userId = UUID.randomUUID();

        Mockito.when(mockActionPlanner.planAction(any(), any())).thenReturn(
                ActionPlanDTO.builder()
                        .goal("Execute daily brief workflow")
                        .steps(List.of(
                                ActionStepDTO.builder()
                                        .stepNumber(1)
                                        .toolName("CalendarTool")
                                        .stepDescription("Check calendar schedule")
                                        .parameters(Map.of())
                                        .build(),
                                ActionStepDTO.builder()
                                        .stepNumber(2)
                                        .toolName("TaskTool")
                                        .stepDescription("Check priority tasks")
                                        .parameters(Map.of())
                                        .build()
                        ))
                        .confidenceScore(0.98)
                        .build()
        );

        WorkflowRuntimeServiceImpl service = new WorkflowRuntimeServiceImpl(mockActionPlanner);

        WorkflowExecutionPlanDTO plan = service.planWorkflow(userId, "Generate daily brief every morning");
        assertNotNull(plan);
        assertTrue(plan.getSteps().size() >= 3);
        assertEquals("SEQUENTIAL", plan.getWorkflowType());
    }

    @Test
    @DisplayName("Should execute workflow instance through state transitions to COMPLETED state")
    void shouldExecuteWorkflowInstance() {
        ActionPlannerService mockActionPlanner = Mockito.mock(ActionPlannerService.class);
        UUID userId = UUID.randomUUID();

        Mockito.when(mockActionPlanner.planAction(any(), any())).thenReturn(
                ActionPlanDTO.builder().goal("Execute").steps(List.of()).confidenceScore(0.95).build()
        );

        WorkflowRuntimeServiceImpl service = new WorkflowRuntimeServiceImpl(mockActionPlanner);

        WorkflowInstanceDTO instance = service.executeWorkflow(userId, "Daily Brief Workflow", "Generate brief");

        assertNotNull(instance);
        assertEquals("COMPLETED", instance.getCurrentState());
        assertEquals(100, instance.getProgressPercentage());
    }

    @Test
    @DisplayName("Should register and list specialized AI agents in Agent Runtime")
    void shouldManageAgentRuntime() {
        ActionPlannerService mockActionPlanner = Mockito.mock(ActionPlannerService.class);
        WorkflowRuntimeServiceImpl service = new WorkflowRuntimeServiceImpl(mockActionPlanner);

        List<AgentRegistrationDTO> agents = service.getRegisteredAgents();

        assertNotNull(agents);
        assertTrue(agents.size() >= 3);
        assertTrue(agents.stream().anyMatch(a -> a.getAgentName().equals("ExecutiveAssistantAgent")));
    }
}
