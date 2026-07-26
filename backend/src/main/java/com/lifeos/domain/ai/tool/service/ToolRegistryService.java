package com.lifeos.domain.ai.tool.service;

import com.lifeos.domain.ai.tool.dto.ToolDefinitionDTO;

import java.util.List;

/**
 * Service interface for Tool Registry & Capability Discovery.
 */
public interface ToolRegistryService {

    List<ToolDefinitionDTO> getRegisteredTools();

    List<String> getToolCategories();

    ToolDefinitionDTO getToolByName(String name);
}
