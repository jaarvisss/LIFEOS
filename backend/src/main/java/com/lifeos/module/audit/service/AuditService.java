package com.lifeos.module.audit.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.audit.dto.ActivityLogResponse;
import com.lifeos.module.audit.dto.AuditLogResponse;
import com.lifeos.module.audit.dto.EntityHistoryResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Audit Trail, Revision History, and User Activity tracking.
 */
public interface AuditService {

    PagedResponse<AuditLogResponse> getUserAuditLogs(UUID userId, PaginationRequest paginationRequest);

    PagedResponse<AuditLogResponse> getEntityAuditLogs(String entityName, UUID entityId, PaginationRequest paginationRequest);

    List<EntityHistoryResponse> getEntityHistory(String entityName, UUID entityId);

    PagedResponse<ActivityLogResponse> getUserActivityLogs(UUID userId, PaginationRequest paginationRequest);

    void recordAuditLog(UUID userId, String entityName, UUID entityId, String action, String diffJsonb, String ipAddress);

    void recordActivityLog(UUID userId, String activityType, String description, String metadataJsonb);
}
