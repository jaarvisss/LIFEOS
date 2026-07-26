package com.lifeos.domain.ai.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for Workflow Definitions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDefinitionDTO {
    private UUID id;
    private String name;
    private String description;
    private String workflowType; // SEQUENTIAL, PARALLEL, CONDITIONAL, APPROVAL, SCHEDULED, RECURRING
    private String version;
    private String definitionJson;
}
