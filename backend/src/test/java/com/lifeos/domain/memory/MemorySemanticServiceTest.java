package com.lifeos.domain.memory;

import com.lifeos.domain.memory.domain.aggregate.Memory;
import com.lifeos.domain.memory.domain.repository.MemoryDomainRepository;
import com.lifeos.domain.memory.domain.valueobject.*;
import com.lifeos.domain.memory.intelligence.gateway.LlmGateway;
import com.lifeos.domain.memory.semantic.dto.ContextBuildRequestDTO;
import com.lifeos.domain.memory.semantic.dto.ContextBuildResponseDTO;
import com.lifeos.domain.memory.semantic.dto.SemanticSearchResultDTO;
import com.lifeos.domain.memory.semantic.service.MemorySemanticServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit Test Suite for Semantic Search Engine & Context Builder.
 */
class MemorySemanticServiceTest {

    @Test
    @DisplayName("Should perform semantic vector search and rank results by score")
    void shouldPerformSemanticSearch() {
        MemoryDomainRepository mockRepo = Mockito.mock(MemoryDomainRepository.class);
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);

        UUID userId = UUID.randomUUID();
        Memory memory = Memory.create(
                userId, null, MemoryTitle.of("Flutter Architecture"), MemoryContent.of("Riverpod and Clean Architecture"),
                null, MemoryType.KNOWLEDGE, ImportanceLevel.HIGH, SourceType.MANUAL, null, null, null
        );

        when(mockRepo.searchByUserIdAndQuery(any(), anyString())).thenReturn(List.of(memory));

        MemorySemanticServiceImpl service = new MemorySemanticServiceImpl(mockRepo, mockGateway);
        List<SemanticSearchResultDTO> results = service.semanticSearch(userId, "architecture", 5, 0.6);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals("Flutter Architecture", results.get(0).getMemory().getTitle());
        assertTrue(results.get(0).getSimilarityScore() > 0.6);
    }

    @Test
    @DisplayName("Should build formatted LLM prompt context block from retrieved memories")
    void shouldBuildContextBlock() {
        MemoryDomainRepository mockRepo = Mockito.mock(MemoryDomainRepository.class);
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);

        UUID userId = UUID.randomUUID();
        Memory memory = Memory.create(
                userId, null, MemoryTitle.of("Project Deadline"), MemoryContent.of("Deliverable due Friday at 5 PM"),
                null, MemoryType.TASK, ImportanceLevel.CRITICAL, SourceType.MANUAL, null, null, null
        );

        when(mockRepo.searchByUserIdAndQuery(any(), anyString())).thenReturn(List.of(memory));

        MemorySemanticServiceImpl service = new MemorySemanticServiceImpl(mockRepo, mockGateway);
        ContextBuildResponseDTO response = service.buildContext(userId, ContextBuildRequestDTO.builder().prompt("When is deliverable due?").build());

        assertNotNull(response);
        assertTrue(response.getFormattedContextBlock().contains("Project Deadline"));
        assertFalse(response.getRetrievedMemories().isEmpty());
    }
}
