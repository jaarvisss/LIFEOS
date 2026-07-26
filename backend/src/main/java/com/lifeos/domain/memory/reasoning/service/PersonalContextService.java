package com.lifeos.domain.memory.reasoning.service;

import java.util.Map;
import java.util.UUID;

/**
 * Service interface for Aggregating Multi-Domain Personal Context.
 */
public interface PersonalContextService {

    Map<String, Object> getFullPersonalContext(UUID userId);
}
