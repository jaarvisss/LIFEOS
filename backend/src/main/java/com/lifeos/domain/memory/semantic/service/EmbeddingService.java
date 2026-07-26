package com.lifeos.domain.memory.semantic.service;

import java.util.UUID;

/**
 * Service interface for generating and indexing vector embeddings.
 */
public interface EmbeddingService {

    float[] generateEmbedding(String text);

    void indexMemoryEmbedding(UUID memoryId, String text);

    void reindexAllMemories(UUID userId);
}
