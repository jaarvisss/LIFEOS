package com.lifeos.domain.memory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifeos.domain.memory.intelligence.dto.ExtractedMemoryDTO;

import com.lifeos.domain.memory.intelligence.gateway.LlmGateway;
import com.lifeos.domain.memory.intelligence.gateway.LlmResponse;
import com.lifeos.domain.memory.intelligence.service.MemoryIntelligenceServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit Test Suite for Memory Intelligence Service.
 */
class MemoryIntelligenceServiceTest {

    @Test
    @DisplayName("Should extract structured memory from raw text using LLM Gateway")
    void shouldExtractStructuredMemory() {
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);
        ObjectMapper objectMapper = new ObjectMapper();

        String sampleLlmResponse = """
        {
          "title": "Meeting with Rahul",
          "summary": "Scheduled meeting with Rahul tomorrow at 4 PM",
          "content": "I have a meeting with Rahul tomorrow at 4 PM.",
          "memoryType": "EVENT",
          "importanceLevel": "HIGH",
          "importanceScore": 85,
          "importanceReasoning": "Time-sensitive client meeting",
          "confidence": 0.98,
          "language": "en",
          "tags": ["meeting", "rahul", "work"],
          "source": "MANUAL"
        }
        """;

        when(mockGateway.generateText(any())).thenReturn(
                LlmResponse.builder()
                        .content(sampleLlmResponse)
                        .successful(true)
                        .latencyMs(120)
                        .build()
        );

        MemoryIntelligenceServiceImpl service = new MemoryIntelligenceServiceImpl(mockGateway, objectMapper);
        ExtractedMemoryDTO result = service.extractMemory("I have a meeting with Rahul tomorrow at 4 PM.");

        assertNotNull(result);
        assertEquals("Meeting with Rahul", result.getTitle());
        assertEquals("EVENT", result.getMemoryType());
        assertEquals("HIGH", result.getImportanceLevel());
        assertEquals(85, result.getImportanceScore());
        assertTrue(result.getTags().contains("meeting"));
    }
}
