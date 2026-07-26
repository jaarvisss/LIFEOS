package com.lifeos.domain.memory.application.service;

import com.lifeos.common.response.PagedResponse;
import com.lifeos.domain.memory.application.mapper.MemoryDtoMapper;
import com.lifeos.domain.memory.domain.aggregate.Memory;
import com.lifeos.domain.memory.domain.exception.MemoryNotFoundException;
import com.lifeos.domain.memory.domain.exception.MemoryValidationException;
import com.lifeos.domain.memory.domain.repository.MemoryDomainRepository;
import com.lifeos.domain.memory.domain.valueobject.*;
import com.lifeos.domain.memory.presentation.request.CreateMemoryRequest;
import com.lifeos.domain.memory.presentation.request.UpdateMemoryRequest;
import com.lifeos.domain.memory.presentation.response.MemoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Enterprise Application Service Implementation for Memory Management.
 */
@Service
@RequiredArgsConstructor
public class MemoryServiceImpl implements MemoryService {

    private final MemoryDomainRepository memoryRepository;

    private static final long MAX_PINNED_LIMIT = 50;
    private static final long MAX_FAVORITES_LIMIT = 100;

    @Override
    @Transactional
    public MemoryResponse createMemory(UUID userId, CreateMemoryRequest request) {
        MemoryTitle title = MemoryTitle.of(request.getTitle());
        MemoryContent content = MemoryContent.of(request.getContent());
        MemorySummary summary = request.getSummary() != null ? MemorySummary.of(request.getSummary()) : MemorySummary.empty();

        MemoryType type = request.getMemoryType() != null ? MemoryType.valueOf(request.getMemoryType().toUpperCase()) : MemoryType.NOTE;
        ImportanceLevel importance = request.getImportanceLevel() != null ? ImportanceLevel.valueOf(request.getImportanceLevel().toUpperCase()) : ImportanceLevel.NORMAL;
        SourceType source = request.getSourceType() != null ? SourceType.valueOf(request.getSourceType().toUpperCase()) : SourceType.MANUAL;

        Language language = request.getLanguage() != null ? Language.of(request.getLanguage()) : Language.defaultLanguage();
        ConfidenceScore confidenceScore = request.getConfidenceScore() != null ? ConfidenceScore.of(request.getConfidenceScore()) : ConfidenceScore.defaultScore();

        Memory memory = Memory.create(
                userId,
                request.getLifeProfileId(),
                title,
                content,
                summary,
                type,
                importance,
                source,
                language,
                confidenceScore,
                request.getMetadata()
        );

        Memory saved = memoryRepository.save(memory);
        return MemoryDtoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MemoryResponse updateMemory(UUID userId, UUID id, UpdateMemoryRequest request) {
        Memory memory = memoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new MemoryNotFoundException(id));

        MemoryTitle title = request.getTitle() != null ? MemoryTitle.of(request.getTitle()) : null;
        MemoryContent content = request.getContent() != null ? MemoryContent.of(request.getContent()) : null;
        MemorySummary summary = request.getSummary() != null ? MemorySummary.of(request.getSummary()) : null;
        MemoryType type = request.getMemoryType() != null ? MemoryType.valueOf(request.getMemoryType().toUpperCase()) : null;
        ImportanceLevel importance = request.getImportanceLevel() != null ? ImportanceLevel.valueOf(request.getImportanceLevel().toUpperCase()) : null;
        Language language = request.getLanguage() != null ? Language.of(request.getLanguage()) : null;

        memory.update(title, content, summary, type, importance, language, request.getMetadata());

        Memory saved = memoryRepository.save(memory);
        return MemoryDtoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MemoryResponse getMemoryById(UUID userId, UUID id) {
        Memory memory = memoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new MemoryNotFoundException(id));

        memory.recordAccess();
        Memory saved = memoryRepository.save(memory);
        return MemoryDtoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MemoryResponse> getAllMemories(UUID userId, String status, String type, int page, int size) {
        PagedResponse<Memory> domainPaged = memoryRepository.findByUserId(userId, status, type, page, size);
        List<MemoryResponse> responses = domainPaged.getContent().stream()
                .map(MemoryDtoMapper::toResponse)
                .collect(Collectors.toList());

        return PagedResponse.of(responses, domainPaged.getPage(), domainPaged.getSize(), domainPaged.getTotalElements(), domainPaged.getTotalPages());
    }

    @Override
    @Transactional
    public void deleteMemory(UUID userId, UUID id) {
        Memory memory = memoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new MemoryNotFoundException(id));

        memory.delete();
        memoryRepository.save(memory);
    }

    @Override
    @Transactional
    public MemoryResponse archiveMemory(UUID userId, UUID id) {
        Memory memory = memoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new MemoryNotFoundException(id));

        memory.archive();
        Memory saved = memoryRepository.save(memory);
        return MemoryDtoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MemoryResponse restoreMemory(UUID userId, UUID id) {
        Memory memory = memoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new MemoryNotFoundException(id));

        memory.restore();
        Memory saved = memoryRepository.save(memory);
        return MemoryDtoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MemoryResponse favoriteMemory(UUID userId, UUID id) {
        Memory memory = memoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new MemoryNotFoundException(id));

        if (!memory.isFavorite()) {
            long currentFavorites = memoryRepository.countFavoritesByUserId(userId);
            if (currentFavorites >= MAX_FAVORITES_LIMIT) {
                throw new MemoryValidationException("Favorite limit of " + MAX_FAVORITES_LIMIT + " memories reached");
            }
        }

        memory.toggleFavorite();
        Memory saved = memoryRepository.save(memory);
        return MemoryDtoMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MemoryResponse pinMemory(UUID userId, UUID id) {
        Memory memory = memoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new MemoryNotFoundException(id));

        if (!memory.isPinned()) {
            long currentPinned = memoryRepository.countPinnedByUserId(userId);
            if (currentPinned >= MAX_PINNED_LIMIT) {
                throw new MemoryValidationException("Pinned limit of " + MAX_PINNED_LIMIT + " memories reached");
            }
            memory.setPinned(true);
        } else {
            memory.setPinned(false);
        }

        Memory saved = memoryRepository.save(memory);
        return MemoryDtoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemoryResponse> getRecentMemories(UUID userId) {
        return memoryRepository.findRecentlyAccessedByUserId(userId, 10).stream()
                .map(MemoryDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemoryResponse> getFavoriteMemories(UUID userId) {
        return memoryRepository.findFavoritesByUserId(userId).stream()
                .map(MemoryDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemoryResponse> getPinnedMemories(UUID userId) {
        return memoryRepository.findPinnedByUserId(userId).stream()
                .map(MemoryDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
