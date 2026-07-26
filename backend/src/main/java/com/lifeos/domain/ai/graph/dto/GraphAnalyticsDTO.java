package com.lifeos.domain.ai.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Knowledge Graph Topology & Observability Metrics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphAnalyticsDTO {
    private long totalNodes;
    private long totalEdges;
    private int clusterCount;
    private String mostConnectedEntity;
    private double graphDensity;
}
