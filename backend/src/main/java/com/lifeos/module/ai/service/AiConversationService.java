package com.lifeos.module.ai.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.ai.dto.AiConversationRequest;
import com.lifeos.module.ai.dto.AiConversationResponse;
import com.lifeos.module.ai.dto.AiMessageRequest;
import com.lifeos.module.ai.dto.AiMessageResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for AI Conversation & Message processing.
 */
public interface AiConversationService {

    AiConversationResponse createConversation(UUID userId, AiConversationRequest request);

    AiConversationResponse getConversationById(UUID userId, UUID id);

    PagedResponse<AiConversationResponse> getConversations(UUID userId, PaginationRequest paginationRequest);

    AiConversationResponse updateConversation(UUID userId, UUID id, AiConversationRequest request);

    void deleteConversation(UUID userId, UUID id);

    AiMessageResponse sendMessage(UUID userId, AiMessageRequest request);

    List<AiMessageResponse> getConversationMessages(UUID userId, UUID conversationId);
}
