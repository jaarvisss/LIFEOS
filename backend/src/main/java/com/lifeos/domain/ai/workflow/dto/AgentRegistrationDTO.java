package com.lifeos.domain.ai.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for Specialized AI Agent Registration.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentRegistrationDTO {
    private UUID id;
    private String agentName;
    private String agentRole; // Executive Assistant, Financial Advisor, Health Coach, Task Manager
    private String description;
    private String status; // ACTIVE, SUSPENDED, TERMINATED
}
