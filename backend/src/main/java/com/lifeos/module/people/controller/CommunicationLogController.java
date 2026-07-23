package com.lifeos.module.people.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.people.dto.CommunicationLogRequest;
import com.lifeos.module.people.dto.CommunicationLogResponse;
import com.lifeos.module.people.service.CommunicationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller for Communication Logs.
 */
@RestController
@RequestMapping("/api/v1/communication-logs")
@RequiredArgsConstructor
@Tag(name = "Communication Logs", description = "Endpoints for logging and tracking interaction history with contacts")
public class CommunicationLogController {

    private final CommunicationLogService communicationLogService;

    @PostMapping
    @Operation(summary = "Log Communication", description = "Records a new communication interaction log.")
    public ResponseEntity<ApiResponse<CommunicationLogResponse>> logCommunication(@Valid @RequestBody CommunicationLogRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(communicationLogService.logCommunication(userId, request), "Communication logged successfully");
    }

    @GetMapping
    @Operation(summary = "Get All Logs", description = "Retrieves paginated list of interaction logs.")
    public ResponseEntity<ApiResponse<PagedResponse<CommunicationLogResponse>>> getAllLogs(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(communicationLogService.getAllLogs(userId, paginationRequest));
    }

    @GetMapping("/contact/{contactId}")
    @Operation(summary = "Get Logs by Contact", description = "Retrieves communication logs for a specific contact.")
    public ResponseEntity<ApiResponse<PagedResponse<CommunicationLogResponse>>> getLogsByContact(
            @PathVariable UUID contactId,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(communicationLogService.getLogsByContact(userId, contactId, paginationRequest));
    }
}
