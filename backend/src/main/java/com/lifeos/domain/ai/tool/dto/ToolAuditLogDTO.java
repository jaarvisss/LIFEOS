package com.lifeos.domain.ai.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Tool Execution Audit Logging.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolAuditLogDTO {
    private UUID id;
    private UUID userId;
    private String toolName;
    private UUID executionId;
    private String actionPerformed;
    private LocalDateTime timestamp;
}
