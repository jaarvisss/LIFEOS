package com.lifeos.module.audit.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.audit.dto.ActivityLogResponse;
import com.lifeos.module.audit.dto.AuditLogResponse;
import com.lifeos.module.audit.dto.EntityHistoryResponse;
import com.lifeos.module.audit.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Audit Engine (Audit Logs, Entity Revision History, Activity Tracking).
 */
@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
@Tag(name = "Audit & Governance", description = "Endpoints for user audit logs, entity version history, and system activity logs")
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/user")
    @Operation(summary = "Get User Audit Logs", description = "Retrieves user's partitioned audit event logs.")
    public ResponseEntity<ApiResponse<PagedResponse<AuditLogResponse>>> getUserAuditLogs(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(auditService.getUserAuditLogs(userId, paginationRequest));
    }

    @GetMapping("/entity")
    @Operation(summary = "Get Entity Audit Logs", description = "Retrieves audit trail for a specific entity.")
    public ResponseEntity<ApiResponse<PagedResponse<AuditLogResponse>>> getEntityAuditLogs(
            @RequestParam String entityName,
            @RequestParam UUID entityId,
            @Valid PaginationRequest paginationRequest) {
        return ResponseBuilder.ok(auditService.getEntityAuditLogs(entityName, entityId, paginationRequest));
    }

    @GetMapping("/history/{entityName}/{entityId}")
    @Operation(summary = "Get Entity Revision History", description = "Retrieves version revision history snapshots for an entity.")
    public ResponseEntity<ApiResponse<List<EntityHistoryResponse>>> getEntityHistory(
            @PathVariable String entityName,
            @PathVariable UUID entityId) {
        return ResponseBuilder.ok(auditService.getEntityHistory(entityName, entityId));
    }

    @GetMapping("/activity")
    @Operation(summary = "Get User Activity Logs", description = "Retrieves user activity log history.")
    public ResponseEntity<ApiResponse<PagedResponse<ActivityLogResponse>>> getUserActivityLogs(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(auditService.getUserActivityLogs(userId, paginationRequest));
    }
}
