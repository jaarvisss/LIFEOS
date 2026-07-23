package com.lifeos.module.ai.service;

import com.lifeos.module.ai.dto.VectorSearchRequest;
import com.lifeos.module.ai.dto.VectorSearchResult;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for pgvector similarity search & embeddings retrieval.
 */
public interface VectorSearchService {

    List<VectorSearchResult> searchSemanticVector(UUID userId, VectorSearchRequest request);

    void storeEmbedding(UUID userId, String sourceType, UUID sourceId, String chunkContent);
}
