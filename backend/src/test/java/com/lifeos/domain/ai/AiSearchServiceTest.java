package com.lifeos.domain.ai;

import com.lifeos.domain.ai.search.dto.*;
import com.lifeos.domain.ai.search.service.SearchPlatformServiceImpl;
import com.lifeos.domain.memory.intelligence.gateway.LlmGateway;
import com.lifeos.domain.memory.intelligence.gateway.LlmResponse;
import com.lifeos.domain.memory.semantic.dto.ContextBuildResponseDTO;
import com.lifeos.domain.memory.semantic.service.ContextBuilderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Unit Test Suite for Universal Search Platform & Direct AI Answer Mode.
 */
class AiSearchServiceTest {

    @Test
    @DisplayName("Should execute hybrid search across core LifeOS domains")
    void shouldExecuteHybridSearch() {
        ContextBuilderService mockContextBuilder = Mockito.mock(ContextBuilderService.class);
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);

        Mockito.when(mockContextBuilder.buildContext(any(), any())).thenReturn(
                ContextBuildResponseDTO.builder()
                        .formattedContextBlock("Retrieved records for query")
                        .retrievedMemories(List.of())
                        .build()
        );

        SearchPlatformServiceImpl service = new SearchPlatformServiceImpl(mockContextBuilder, mockGateway);
        UUID userId = UUID.randomUUID();

        SearchResultResponseDTO response = service.executeSearch(userId, SearchRequestDTO.builder()
                .query("Show everything related to Rahul")
                .build());

        assertNotNull(response);
        assertEquals("Show everything related to Rahul", response.getQuery());
        assertTrue(response.getTotalResults() >= 3);
        assertTrue(response.getItems().stream().anyMatch(i -> i.getDomainType().equals("MEMORY")));
    }

    @Test
    @DisplayName("Should generate direct cited AI answer Synthesizing RAG records")
    void shouldGenerateDirectAiAnswer() {
        ContextBuilderService mockContextBuilder = Mockito.mock(ContextBuilderService.class);
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);

        Mockito.when(mockContextBuilder.buildContext(any(), any())).thenReturn(
                ContextBuildResponseDTO.builder().formattedContextBlock("Rahul meeting context").retrievedMemories(List.of()).build()
        );

        Mockito.when(mockGateway.generateText(any())).thenReturn(
                LlmResponse.builder()
                        .content("Rahul is involved in the Identity Builders project and you had a meeting with him on Tuesday.")
                        .successful(true)
                        .build()
        );

        SearchPlatformServiceImpl service = new SearchPlatformServiceImpl(mockContextBuilder, mockGateway);
        UUID userId = UUID.randomUUID();

        SearchAnswerResponseDTO answer = service.generateAiAnswer(userId, SearchRequestDTO.builder()
                .query("What projects involve Rahul?")
                .build());

        assertNotNull(answer);
        assertTrue(answer.getDirectAnswer().contains("Identity Builders"));
        assertFalse(answer.getSupportingRecords().isEmpty());
    }
}
