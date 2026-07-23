package com.lifeos.module.ai.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.module.ai.dto.AiSuggestionResponse;
import com.lifeos.module.ai.entity.AiSuggestionEntity;
import com.lifeos.module.ai.mapper.AiMapper;
import com.lifeos.module.ai.repository.AiSuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of AiSuggestionService interface.
 */
@Service
@RequiredArgsConstructor
public class AiSuggestionServiceImpl implements AiSuggestionService {

    private final AiSuggestionRepository aiSuggestionRepository;
    private final AiMapper aiMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AiSuggestionResponse> getActiveSuggestions(UUID userId) {
        return aiMapper.toSuggestionDtoList(
                aiSuggestionRepository.findByUserIdAndIsAcceptedIsNullAndIsDeletedFalseOrderByConfidenceScoreDesc(userId)
        );
    }

    @Override
    @Transactional
    public AiSuggestionResponse respondToSuggestion(UUID userId, UUID suggestionId, boolean accept) {
        AiSuggestionEntity suggestion = aiSuggestionRepository.findByIdAndUserIdAndIsDeletedFalse(suggestionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("AiSuggestion", "id", suggestionId));

        suggestion.setIsAccepted(accept);
        if (!accept) {
            suggestion.setDismissedAt(Instant.now());
        }
        suggestion = aiSuggestionRepository.save(suggestion);
        return aiMapper.toDto(suggestion);
    }
}
