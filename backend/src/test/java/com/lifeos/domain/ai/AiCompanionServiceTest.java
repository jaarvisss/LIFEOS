package com.lifeos.domain.ai;

import com.lifeos.domain.ai.companion.dto.ChatMessageRequestDTO;
import com.lifeos.domain.ai.companion.dto.ChatMessageResponseDTO;
import com.lifeos.domain.ai.companion.dto.ConversationSummaryDTO;
import com.lifeos.domain.ai.companion.service.ConversationServiceImpl;
import com.lifeos.domain.memory.intelligence.gateway.LlmGateway;
import com.lifeos.domain.memory.intelligence.gateway.LlmResponse;
import com.lifeos.domain.memory.reasoning.dto.ReasoningResponseDTO;
import com.lifeos.domain.memory.reasoning.service.ReasoningService;
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
 * Unit Test Suite for AI Companion Conversational Platform.
 */
class AiCompanionServiceTest {

    @Test
    @DisplayName("Should process chat message through RAG retrieval, Reasoning Engine, and Gemini Gateway")
    void shouldProcessChatMessage() {
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);
        ContextBuilderService mockContextBuilder = Mockito.mock(ContextBuilderService.class);
        ReasoningService mockReasoningService = Mockito.mock(ReasoningService.class);

        UUID userId = UUID.randomUUID();

        Mockito.when(mockContextBuilder.buildContext(any(), any())).thenReturn(
                ContextBuildResponseDTO.builder()
                        .formattedContextBlock("Memory Context: Task due Friday")
                        .retrievedMemories(List.of())
                        .build()
        );

        Mockito.when(mockReasoningService.reasonAboutPrompt(any(), any())).thenReturn(
                ReasoningResponseDTO.builder()
                        .conclusion("Priority task execution required")
                        .confidenceScore(0.96)
                        .build()
        );

        Mockito.when(mockGateway.generateText(any())).thenReturn(
                LlmResponse.builder()
                        .content("I've checked your schedule and tasks. You have a deadline on Friday at 5 PM.")
                        .successful(true)
                        .build()
        );

        ConversationServiceImpl service = new ConversationServiceImpl(mockGateway, mockContextBuilder, mockReasoningService);

        ChatMessageResponseDTO response = service.processChatMessage(userId, ChatMessageRequestDTO.builder()
                .message("What are my deadlines?")
                .build());

        assertNotNull(response);
        assertEquals("ASSISTANT", response.getSenderType());
        assertTrue(response.getContent().contains("Friday at 5 PM"));
        assertFalse(response.getSuggestedActions().isEmpty());
    }

    @Test
    @DisplayName("Should manage conversation session lifecycle (Create, Favorite, Rename)")
    void shouldManageConversationLifecycle() {
        LlmGateway mockGateway = Mockito.mock(LlmGateway.class);
        ContextBuilderService mockContextBuilder = Mockito.mock(ContextBuilderService.class);
        ReasoningService mockReasoningService = Mockito.mock(ReasoningService.class);

        UUID userId = UUID.randomUUID();
        ConversationServiceImpl service = new ConversationServiceImpl(mockGateway, mockContextBuilder, mockReasoningService);

        ConversationSummaryDTO conv = service.createConversation(userId, "Project Sprint Chat");
        assertNotNull(conv);
        assertEquals("Project Sprint Chat", conv.getTitle());

        ConversationSummaryDTO renamed = service.renameConversation(userId, conv.getId(), "Updated Title");
        assertEquals("Updated Title", renamed.getTitle());

        ConversationSummaryDTO fav = service.favoriteConversation(userId, conv.getId());
        assertTrue(fav.isFavorite());
    }
}
