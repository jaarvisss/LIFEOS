package com.lifeos.domain.ai.companion.service;

import com.lifeos.domain.ai.companion.dto.*;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for AI Companion Conversation Management.
 */
public interface ConversationService {

    ChatMessageResponseDTO processChatMessage(UUID userId, ChatMessageRequestDTO request);

    ConversationSummaryDTO createConversation(UUID userId, String title);

    List<ConversationSummaryDTO> getUserConversations(UUID userId);

    ConversationSummaryDTO getConversationById(UUID userId, UUID conversationId);

    void deleteConversation(UUID userId, UUID conversationId);

    ConversationSummaryDTO renameConversation(UUID userId, UUID conversationId, String newTitle);

    ConversationSummaryDTO archiveConversation(UUID userId, UUID conversationId);

    ConversationSummaryDTO favoriteConversation(UUID userId, UUID conversationId);

    void recordFeedback(UUID userId, UUID conversationId, FeedbackRequestDTO feedback);

    String generateConversationSummary(UUID userId, UUID conversationId);
}
