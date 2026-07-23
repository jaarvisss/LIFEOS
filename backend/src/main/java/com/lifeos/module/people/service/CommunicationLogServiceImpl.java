package com.lifeos.module.people.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.people.dto.CommunicationLogRequest;
import com.lifeos.module.people.dto.CommunicationLogResponse;
import com.lifeos.module.people.entity.CommunicationLogEntity;
import com.lifeos.module.people.mapper.CommunicationLogMapper;
import com.lifeos.module.people.repository.CommunicationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Implementation of CommunicationLogService interface.
 */
@Service
@RequiredArgsConstructor
public class CommunicationLogServiceImpl implements CommunicationLogService {

    private final CommunicationLogRepository communicationLogRepository;
    private final CommunicationLogMapper communicationLogMapper;

    @Override
    @Transactional
    public CommunicationLogResponse logCommunication(UUID userId, CommunicationLogRequest request) {
        CommunicationLogEntity entity = communicationLogMapper.toEntity(request);
        entity.setUserId(userId);
        if (entity.getInteractionDate() == null) {
            entity.setInteractionDate(Instant.now());
        }
        entity = communicationLogRepository.save(entity);
        return communicationLogMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<CommunicationLogResponse> getLogsByContact(UUID userId, UUID contactId, PaginationRequest paginationRequest) {
        Page<CommunicationLogEntity> page = communicationLogRepository.findByUserIdAndContactId(userId, contactId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(communicationLogMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<CommunicationLogResponse> getAllLogs(UUID userId, PaginationRequest paginationRequest) {
        Page<CommunicationLogEntity> page = communicationLogRepository.findByUserId(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(communicationLogMapper::toDto));
    }
}
