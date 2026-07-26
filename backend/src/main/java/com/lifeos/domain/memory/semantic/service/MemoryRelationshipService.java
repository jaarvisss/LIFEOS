package com.lifeos.domain.memory.semantic.service;

import com.lifeos.domain.memory.semantic.dto.MemoryRelationshipDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Memory Relationship Graph operations.
 */
public interface MemoryRelationshipService {

    MemoryRelationshipDTO linkMemories(UUID sourceId, UUID targetId, String relationshipType, double confidence);

    List<MemoryRelationshipDTO> getRelationshipsForMemory(UUID memoryId);

    List<MemoryRelationshipDTO> getRelatedMemories(UUID memoryId);
}
