package com.lifeos.domain.ai.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * DTO for Knowledge Graph Community Clusters.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphClusterDTO {
    private UUID id;
    private String clusterName;
    private List<GraphNodeDTO> nodes;
}
