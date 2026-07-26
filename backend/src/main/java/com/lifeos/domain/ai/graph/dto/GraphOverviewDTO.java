package com.lifeos.domain.ai.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Knowledge Graph Visual Overview.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphOverviewDTO {
    private List<GraphNodeDTO> nodes;
    private List<GraphEdgeDTO> edges;
    private List<GraphClusterDTO> clusters;
}
