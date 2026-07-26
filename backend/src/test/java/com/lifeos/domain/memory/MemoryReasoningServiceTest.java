package com.lifeos.domain.memory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifeos.domain.memory.domain.aggregate.Memory;
import com.lifeos.domain.memory.domain.repository.MemoryDomainRepository;
import com.lifeos.domain.memory.domain.valueobject.*;
import com.lifeos.domain.memory.intelligence.gateway.LlmGateway;
import com.lifeos.domain.memory.intelligence.gateway.LlmResponse;
import com.lifeos.domain.memory.reasoning.dto.DailyBriefResponseDTO;
import com.lifeos.domain.memory.reasoning.dto.PlanResponseDTO;
import com.lifeos.domain.memory.reasoning.dto.ReasoningResponseDTO;
import com.lifeos.domain.memory.reasoning.service.MemoryReasoningServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Unit Test Suite for AI Memory Reasoning Engine.
 */
class MemoryReasoningServiceTest {

    @Test
    @DisplayName("Should execute multi-step reasoning over memories and return structured conclusion")
    void shouldExecuteMultiStepReasoning() {
        MemoryDomainRepository mockRepo = Mockito.mock(MemoryDomainRepository.class);
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);
        ObjectMapper objectMapper = new ObjectMapper();

        UUID userId = UUID.randomUUID();
        Memory memory = Memory.create(
                userId, null, MemoryTitle.of("Project Deadline"), MemoryContent.of("Deliverable due Friday at 5 PM"),
                null, MemoryType.TASK, ImportanceLevel.CRITICAL, SourceType.MANUAL, null, null, null
        );

        Mockito.when(mockRepo.findRecentlyAccessedByUserId(any(), Mockito.anyInt())).thenReturn(List.of(memory));
        Mockito.when(mockGateway.generateText(any())).thenReturn(
                LlmResponse.builder()
                        .content("""
                        {
                          "conclusion": "Finish task by Friday 5 PM",
                          "reasoningExplanation": "Critical priority task",
                          "evidenceUsed": ["Task due date"],
                          "confidenceScore": 0.98,
                          "detectedContradictions": [],
                          "alternativeHypotheses": []
                        }
                        """)
                        .successful(true)
                        .build()
        );

        MemoryReasoningServiceImpl service = new MemoryReasoningServiceImpl(mockRepo, mockGateway, objectMapper);
        ReasoningResponseDTO result = service.reasonAboutPrompt(userId, "What are my deadlines?");

        assertNotNull(result);
        assertEquals("Finish task by Friday 5 PM", result.getConclusion());
        assertTrue(result.getConfidenceScore() > 0.9);
    }

    @Test
    @DisplayName("Should generate executive daily AI brief with priorities and proactive insights")
    void shouldGenerateDailyBrief() {
        MemoryDomainRepository mockRepo = Mockito.mock(MemoryDomainRepository.class);
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);
        ObjectMapper objectMapper = new ObjectMapper();

        UUID userId = UUID.randomUUID();

        MemoryReasoningServiceImpl service = new MemoryReasoningServiceImpl(mockRepo, mockGateway, objectMapper);
        DailyBriefResponseDTO brief = service.generateDailyBrief(userId);

        assertNotNull(brief);
        assertEquals("Good Morning, Lalit 👋", brief.getGreeting());
        assertFalse(brief.getScheduleItems().isEmpty());
        assertFalse(brief.getProactiveAlerts().isEmpty());
    }

    @Test
    @DisplayName("Should generate adaptive execution plan for specified timeframe")
    void shouldGenerateAdaptivePlan() {
        MemoryDomainRepository mockRepo = Mockito.mock(MemoryDomainRepository.class);
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);
        ObjectMapper objectMapper = new ObjectMapper();

        UUID userId = UUID.randomUUID();

        MemoryReasoningServiceImpl service = new MemoryReasoningServiceImpl(mockRepo, mockGateway, objectMapper);
        PlanResponseDTO plan = service.generatePlan(userId, "daily", "Flutter Architecture");

        assertNotNull(plan);
        assertTrue(plan.getPlanTitle().contains("DAILY"));
        assertFalse(plan.getPriorityActions().isEmpty());
    }
}
