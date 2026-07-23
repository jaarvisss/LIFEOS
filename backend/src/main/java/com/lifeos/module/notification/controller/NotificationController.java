package com.lifeos.module.notification.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.notification.dto.DeliveryLogResponse;
import com.lifeos.module.notification.dto.NotificationChannelRequest;
import com.lifeos.module.notification.dto.NotificationChannelResponse;
import com.lifeos.module.notification.dto.NotificationPreferenceRequest;
import com.lifeos.module.notification.dto.NotificationPreferenceResponse;
import com.lifeos.module.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Notification Engine (Preferences, Channels, Delivery Logs).
 */
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Engine", description = "Endpoints for quiet hours preferences, delivery channels, and notification logs")
public class NotificationController {

    private final NotificationService notificationService;

    // --- Preferences ---

    @GetMapping("/preferences")
    @Operation(summary = "Get Notification Preferences", description = "Retrieves quiet hours settings.")
    public ResponseEntity<ApiResponse<NotificationPreferenceResponse>> getPreferences() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(notificationService.getPreferences(userId));
    }

    @PutMapping("/preferences")
    @Operation(summary = "Update Notification Preferences", description = "Updates quiet hours settings.")
    public ResponseEntity<ApiResponse<NotificationPreferenceResponse>> updatePreferences(@Valid @RequestBody NotificationPreferenceRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(notificationService.updatePreferences(userId, request), "Preferences updated successfully");
    }

    // --- Channels ---

    @PostMapping("/channels")
    @Operation(summary = "Create Notification Channel", description = "Registers a notification channel (EMAIL, PUSH, SMS).")
    public ResponseEntity<ApiResponse<NotificationChannelResponse>> createChannel(@Valid @RequestBody NotificationChannelRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(notificationService.createChannel(userId, request), "Notification channel created");
    }

    @GetMapping("/channels")
    @Operation(summary = "List Notification Channels", description = "Retrieves user's registered notification channels.")
    public ResponseEntity<ApiResponse<List<NotificationChannelResponse>>> getChannels() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(notificationService.getChannels(userId));
    }

    @DeleteMapping("/channels/{id}")
    @Operation(summary = "Delete Notification Channel", description = "Deletes a notification channel.")
    public ResponseEntity<ApiResponse<Void>> deleteChannel(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        notificationService.deleteChannel(userId, id);
        return ResponseBuilder.ok(null, "Channel deleted successfully");
    }

    // --- Delivery Logs ---

    @GetMapping("/logs")
    @Operation(summary = "List Delivery Logs", description = "Retrieves paginated history of sent notification logs.")
    public ResponseEntity<ApiResponse<PagedResponse<DeliveryLogResponse>>> getDeliveryLogs(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(notificationService.getDeliveryLogs(userId, paginationRequest));
    }
}
