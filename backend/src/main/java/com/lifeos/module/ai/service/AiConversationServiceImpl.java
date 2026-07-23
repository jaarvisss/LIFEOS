package com.lifeos.module.ai.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.ai.dto.AiConversationRequest;
import com.lifeos.module.ai.dto.AiConversationResponse;
import com.lifeos.module.ai.dto.AiMessageRequest;
import com.lifeos.module.ai.dto.AiMessageResponse;
import com.lifeos.module.ai.entity.AiConversationEntity;
import com.lifeos.module.ai.entity.AiMessageEntity;
import com.lifeos.module.ai.mapper.AiMapper;
import com.lifeos.module.ai.repository.AiConversationRepository;
import com.lifeos.module.ai.repository.AiMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of AiConversationService interface.
 */
@Service
@RequiredArgsConstructor
public class AiConversationServiceImpl implements AiConversationService {

    private final AiConversationRepository aiConversationRepository;
    private final AiMessageRepository aiMessageRepository;
    private final AiMapper aiMapper;

    @Override
    @Transactional
    public AiConversationResponse createConversation(UUID userId, AiConversationRequest request) {
        AiConversationEntity conversation = aiMapper.toEntity(request);
        conversation.setUserId(userId);
        if (conversation.getSystemInstruction() == null) {
            conversation.setSystemInstruction("You are LifeOS AI, an intelligent personal operating system assistant.");
        }
        conversation = aiConversationRepository.save(conversation);
        return aiMapper.toDto(conversation);
    }

    @Override
    @Transactional(readOnly = true)
    public AiConversationResponse getConversationById(UUID userId, UUID id) {
        AiConversationEntity conversation = aiConversationRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("AiConversation", "id", id));
        return aiMapper.toDto(conversation);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AiConversationResponse> getConversations(UUID userId, PaginationRequest paginationRequest) {
        Page<AiConversationEntity> page = aiConversationRepository.findByUserIdAndIsDeletedFalseOrderByUpdatedAtDesc(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(aiMapper::toDto));
    }

    @Override
    @Transactional
    public AiConversationResponse updateConversation(UUID userId, UUID id, AiConversationRequest request) {
        AiConversationEntity conversation = aiConversationRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("AiConversation", "id", id));

        aiMapper.updateEntityFromDto(request, conversation);
        conversation = aiConversationRepository.save(conversation);
        return aiMapper.toDto(conversation);
    }

    @Override
    @Transactional
    public void deleteConversation(UUID userId, UUID id) {
        AiConversationEntity conversation = aiConversationRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("AiConversation", "id", id));
        conversation.markDeleted();
        aiConversationRepository.save(conversation);
    }

    @Override
    @Transactional
    public AiMessageResponse sendMessage(UUID userId, AiMessageRequest request) {
        AiConversationEntity conversation = aiConversationRepository.findByIdAndUserIdAndIsDeletedFalse(request.conversationId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("AiConversation", "id", request.conversationId()));

        // Save User Message
        AiMessageEntity userMsg = AiMessageEntity.builder()
                .conversationId(conversation.getId())
                .role("USER")
                .content(request.content())
                .tokenCount(estimateTokens(request.content()))
                .build();
        aiMessageRepository.save(userMsg);

        // Generate AI Assistant Response (Reasoning Engine Simulation / Execution)
        String aiResponseText = "LifeOS AI: Processed your request regarding '" + request.content() + "'. I have synchronized your contextual memory and updated active tasks.";
        AiMessageEntity assistantMsg = AiMessageEntity.builder()
                .conversationId(conversation.getId())
                .role("ASSISTANT")
                .content(aiResponseText)
                .tokenCount(estimateTokens(aiResponseText))
                .build();
        assistantMsg = aiMessageRepository.save(assistantMsg);

        return aiMapper.toDto(assistantMsg);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiMessageResponse> getConversationMessages(UUID userId, UUID conversationId) {
        aiConversationRepository.findByIdAndUserIdAndIsDeletedFalse(conversationId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("AiConversation", "id", conversationId));

        return aiMapper.toMessageDtoList(aiMessageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId));
    }

    private int estimateTokens(String text) {
        if (text == null || text.isBlank()) return 0;
        return text.split("\\s+").length * 4 / 3;
    }
}
