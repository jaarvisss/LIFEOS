package com.lifeos.module.audit.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.audit.dto.ActivityLogResponse;
import com.lifeos.module.audit.dto.AuditLogResponse;
import com.lifeos.module.audit.dto.EntityHistoryResponse;
import com.lifeos.module.audit.entity.ActivityLogEntity;
import com.lifeos.module.audit.entity.AuditLogEntity;
import com.lifeos.module.audit.entity.EntityHistoryEntity;
import com.lifeos.module.audit.mapper.AuditMapper;
import com.lifeos.module.audit.repository.ActivityLogRepository;
import com.lifeos.module.audit.repository.AuditLogRepository;
import com.lifeos.module.audit.repository.EntityHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of AuditService interface.
 */
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final EntityHistoryRepository entityHistoryRepository;
    private final ActivityLogRepository activityLogRepository;
    private final AuditMapper auditMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AuditLogResponse> getUserAuditLogs(UUID userId, PaginationRequest paginationRequest) {
        Page<AuditLogEntity> page = auditLogRepository.findByUserIdOrderByCreatedAtDesc(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(auditMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AuditLogResponse> getEntityAuditLogs(String entityName, UUID entityId, PaginationRequest paginationRequest) {
        Page<AuditLogEntity> page = auditLogRepository.findByEntityNameAndEntityIdOrderByCreatedAtDesc(entityName, entityId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(auditMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntityHistoryResponse> getEntityHistory(String entityName, UUID entityId) {
        List<EntityHistoryEntity> historyList = entityHistoryRepository.findByEntityNameAndEntityIdOrderByVersionNumberDesc(entityName, entityId);
        return auditMapper.toHistoryDtoList(historyList);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ActivityLogResponse> getUserActivityLogs(UUID userId, PaginationRequest paginationRequest) {
        Page<ActivityLogEntity> page = activityLogRepository.findByUserIdOrderByCreatedAtDesc(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(auditMapper::toDto));
    }

    @Override
    @Transactional
    public void recordAuditLog(UUID userId, String entityName, UUID entityId, String action, String diffJsonb, String ipAddress) {
        AuditLogEntity log = AuditLogEntity.builder()
                .userId(userId)
                .entityName(entityName)
                .entityId(entityId)
                .action(action)
                .diffJsonb(diffJsonb)
                .ipAddress(ipAddress)
                .build();
        auditLogRepository.save(log);
    }

    @Override
    @Transactional
    public void recordActivityLog(UUID userId, String activityType, String description, String metadataJsonb) {
        ActivityLogEntity activity = ActivityLogEntity.builder()
                .userId(userId)
                .activityType(activityType)
                .description(description)
                .metadataJsonb(metadataJsonb)
                .build();
        activityLogRepository.save(activity);
    }
}
