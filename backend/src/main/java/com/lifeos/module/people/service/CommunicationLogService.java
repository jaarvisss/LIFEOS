package com.lifeos.module.people.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.people.dto.CommunicationLogRequest;
import com.lifeos.module.people.dto.CommunicationLogResponse;

import java.util.UUID;

/**
 * Service Interface for Communication Logs.
 */
public interface CommunicationLogService {

    CommunicationLogResponse logCommunication(UUID userId, CommunicationLogRequest request);

    PagedResponse<CommunicationLogResponse> getLogsByContact(UUID userId, UUID contactId, PaginationRequest paginationRequest);

    PagedResponse<CommunicationLogResponse> getAllLogs(UUID userId, PaginationRequest paginationRequest);
}
