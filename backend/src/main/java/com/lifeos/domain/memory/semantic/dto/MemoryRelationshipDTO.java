package com.lifeos.domain.memory.semantic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for Memory Relationship Graph Edges.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoryRelationshipDTO {
    private UUID id;
    private UUID sourceMemoryId;
    private UUID targetMemoryId;
    private String relationshipType; // PARENT, CHILD, RELATED, DUPLICATE, DEPENDS_ON, SUPPORTS, CONTRADICTS
    private double confidenceScore;
}
