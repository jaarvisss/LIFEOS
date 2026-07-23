package com.lifeos.module.ai.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.ai.dto.AiMemoryRequest;
import com.lifeos.module.ai.dto.AiMemoryResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for AI Personal Memory Engine.
 */
public interface AiMemoryService {

    AiMemoryResponse storeMemory(UUID userId, AiMemoryRequest request);

    AiMemoryResponse getMemoryById(UUID userId, UUID id);

    List<AiMemoryResponse> getAllMemories(UUID userId);

    PagedResponse<AiMemoryResponse> getMemoriesPaged(UUID userId, PaginationRequest paginationRequest);

    AiMemoryResponse updateMemory(UUID userId, UUID id, AiMemoryRequest request);

    void deleteMemory(UUID userId, UUID id);
}
