package com.lifeos.module.ai.repository;

import com.lifeos.module.ai.entity.AiEmbeddingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for ai.embeddings table (pgvector 1536).
 */
@Repository
public interface AiEmbeddingRepository extends JpaRepository<AiEmbeddingEntity, UUID> {

    List<AiEmbeddingEntity> findByUserIdAndSourceType(UUID userId, String sourceType);

    @Query(value = "SELECT * FROM ai.ai_semantic_search(:userId, CAST(:queryEmbedding AS public.vector), :threshold, :limitCount)", nativeQuery = true)
    List<Object[]> searchSemanticVector(
            @Param("userId") UUID userId,
            @Param("queryEmbedding") String queryEmbedding,
            @Param("threshold") double threshold,
            @Param("limitCount") int limitCount
    );
}
