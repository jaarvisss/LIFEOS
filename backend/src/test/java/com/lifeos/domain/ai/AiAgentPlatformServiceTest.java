package com.lifeos.domain.ai;

import com.lifeos.domain.ai.agent.dto.*;
import com.lifeos.domain.ai.agent.service.AgentPlatformServiceImpl;
import com.lifeos.domain.ai.workflow.dto.WorkflowInstanceDTO;
import com.lifeos.domain.ai.workflow.service.WorkflowExecutionEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Unit Test Suite for Autonomous AI Agent Platform & Multi-Agent Collaboration.
 */
class AiAgentPlatformServiceTest {

    @Test
    @DisplayName("Should discover built-in specialized agents")
    void shouldDiscoverBuiltInAgents() {
        WorkflowExecutionEngine mockWorkflowEngine = Mockito.mock(WorkflowExecutionEngine.class);
        AgentPlatformServiceImpl service = new AgentPlatformServiceImpl(mockWorkflowEngine);

        List<AgentDTO> agents = service.getRegisteredAgents();

        assertNotNull(agents);
        assertTrue(agents.size() >= 10);
        assertTrue(agents.stream().anyMatch(a -> a.getAgentName().equals("DailyPlannerAgent")));
        assertTrue(agents.stream().anyMatch(a -> a.getAgentName().equals("FinanceCoachAgent")));
    }

    @Test
    @DisplayName("Should route user request to best-matched agent based on intent keywords")
    void shouldRouteUserRequest() {
        WorkflowExecutionEngine mockWorkflowEngine = Mockito.mock(WorkflowExecutionEngine.class);
        AgentPlatformServiceImpl service = new AgentPlatformServiceImpl(mockWorkflowEngine);
        UUID userId = UUID.randomUUID();

        AgentDTO routedFinance = service.routeUserRequest(userId, "I spent $50 on groceries today");
        assertEquals("FinanceCoachAgent", routedFinance.getAgentName());

        AgentDTO routedHealth = service.routeUserRequest(userId, "I slept 8 hours and completed my workout");
        assertEquals("HealthCoachAgent", routedHealth.getAgentName());

        AgentDTO routedPlanner = service.routeUserRequest(userId, "Plan my upcoming week schedule");
        assertEquals("DailyPlannerAgent", routedPlanner.getAgentName());
    }

    @Test
    @DisplayName("Should execute multi-agent collaboration pipeline through Workflow Execution Engine")
    void shouldExecuteMultiAgentPipeline() {
        WorkflowExecutionEngine mockWorkflowEngine = Mockito.mock(WorkflowExecutionEngine.class);
        UUID userId = UUID.randomUUID();

        Mockito.when(mockWorkflowEngine.executeWorkflow(any(), any(), any())).thenReturn(
                WorkflowInstanceDTO.builder()
                        .id(UUID.randomUUID())
                        .name("Agent Workflow: DailyPlannerAgent")
                        .currentState("COMPLETED")
                        .progressPercentage(100)
                        .build()
        );

        AgentPlatformServiceImpl service = new AgentPlatformServiceImpl(mockWorkflowEngine);

        AgentExecutionResponseDTO response = service.executeAgentPipeline(userId, AgentExecutionRequestDTO.builder()
                .prompt("Plan my product launch week")
                .enableMultiAgentCollaboration(true)
                .build());

        assertNotNull(response);
        assertEquals("DailyPlannerAgent", response.getPrimaryAgentName());
        assertEquals("COMPLETED", response.getWorkflowInstance().getCurrentState());
        assertFalse(response.getDelegations().isEmpty());
    }
}
