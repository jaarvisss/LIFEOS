package com.lifeos.module.ai.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.ai.dto.AiMemoryRequest;
import com.lifeos.module.ai.dto.AiMemoryResponse;
import com.lifeos.module.ai.entity.AiMemoryEntity;
import com.lifeos.module.ai.mapper.AiMapper;
import com.lifeos.module.ai.repository.AiMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of AiMemoryService interface.
 */
@Service
@RequiredArgsConstructor
public class AiMemoryServiceImpl implements AiMemoryService {

    private final AiMemoryRepository aiMemoryRepository;
    private final AiMapper aiMapper;

    @Override
    @Transactional
    public AiMemoryResponse storeMemory(UUID userId, AiMemoryRequest request) {
        AiMemoryEntity memory = aiMapper.toEntity(request);
        memory.setUserId(userId);
        memory = aiMemoryRepository.save(memory);
        return aiMapper.toDto(memory);
    }

    @Override
    @Transactional(readOnly = true)
    public AiMemoryResponse getMemoryById(UUID userId, UUID id) {
        AiMemoryEntity memory = aiMemoryRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("AiMemory", "id", id));
        return aiMapper.toDto(memory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiMemoryResponse> getAllMemories(UUID userId) {
        return aiMapper.toMemoryDtoList(aiMemoryRepository.findByUserIdAndIsDeletedFalse(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AiMemoryResponse> getMemoriesPaged(UUID userId, PaginationRequest paginationRequest) {
        Page<AiMemoryEntity> page = aiMemoryRepository.findByUserIdAndIsDeletedFalse(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(aiMapper::toDto));
    }

    @Override
    @Transactional
    public AiMemoryResponse updateMemory(UUID userId, UUID id, AiMemoryRequest request) {
        AiMemoryEntity memory = aiMemoryRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("AiMemory", "id", id));

        aiMapper.updateEntityFromDto(request, memory);
        memory = aiMemoryRepository.save(memory);
        return aiMapper.toDto(memory);
    }

    @Override
    @Transactional
    public void deleteMemory(UUID userId, UUID id) {
        AiMemoryEntity memory = aiMemoryRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("AiMemory", "id", id));
        memory.markDeleted();
        aiMemoryRepository.save(memory);
    }
}
