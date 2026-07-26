package com.lifeos.domain.memory.application.service;

import com.lifeos.common.response.PagedResponse;
import com.lifeos.domain.memory.presentation.request.CreateMemoryRequest;
import com.lifeos.domain.memory.presentation.request.UpdateMemoryRequest;
import com.lifeos.domain.memory.presentation.response.MemoryResponse;

import java.util.List;
import java.util.UUID;

/**
 * Application Service interface for Memory Management.
 */
public interface MemoryService {

    MemoryResponse createMemory(UUID userId, CreateMemoryRequest request);

    MemoryResponse updateMemory(UUID userId, UUID id, UpdateMemoryRequest request);

    MemoryResponse getMemoryById(UUID userId, UUID id);

    PagedResponse<MemoryResponse> getAllMemories(UUID userId, String status, String type, int page, int size);

    void deleteMemory(UUID userId, UUID id);

    MemoryResponse archiveMemory(UUID userId, UUID id);

    MemoryResponse restoreMemory(UUID userId, UUID id);

    MemoryResponse favoriteMemory(UUID userId, UUID id);

    MemoryResponse pinMemory(UUID userId, UUID id);

    List<MemoryResponse> getRecentMemories(UUID userId);

    List<MemoryResponse> getFavoriteMemories(UUID userId);

    List<MemoryResponse> getPinnedMemories(UUID userId);
}
