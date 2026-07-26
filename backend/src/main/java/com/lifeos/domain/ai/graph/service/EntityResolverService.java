package com.lifeos.domain.ai.graph.service;

import com.lifeos.domain.ai.graph.dto.GraphNodeDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Canonical Entity Resolution and Alias Deduplication.
 */
public interface EntityResolverService {

    GraphNodeDTO resolveOrCreateEntity(UUID userId, String entityName, String entityType, List<String> aliases);
}
