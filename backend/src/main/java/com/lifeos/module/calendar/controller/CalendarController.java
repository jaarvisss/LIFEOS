package com.lifeos.module.calendar.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.calendar.dto.CalendarRequest;
import com.lifeos.module.calendar.dto.CalendarResponse;
import com.lifeos.module.calendar.service.CalendarService;
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
 * REST Controller for Master Calendar Management.
 */
@RestController
@RequestMapping("/api/v1/calendars")
@RequiredArgsConstructor
@Tag(name = "Calendar Management", description = "Endpoints for managing personal and shared calendars")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    @Operation(summary = "Create Calendar", description = "Creates a new calendar instance.")
    public ResponseEntity<ApiResponse<CalendarResponse>> createCalendar(@Valid @RequestBody CalendarRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(calendarService.createCalendar(userId, request), "Calendar created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Calendar by ID", description = "Retrieves calendar details.")
    public ResponseEntity<ApiResponse<CalendarResponse>> getCalendarById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(calendarService.getCalendarById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List User Calendars", description = "Retrieves all calendars owned by the current user.")
    public ResponseEntity<ApiResponse<List<CalendarResponse>>> getCalendars() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(calendarService.getCalendarsByUserId(userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Calendar", description = "Updates calendar details.")
    public ResponseEntity<ApiResponse<CalendarResponse>> updateCalendar(
            @PathVariable UUID id,
            @Valid @RequestBody CalendarRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(calendarService.updateCalendar(userId, id, request), "Calendar updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Calendar", description = "Soft-deletes a calendar instance.")
    public ResponseEntity<ApiResponse<Void>> deleteCalendar(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        calendarService.deleteCalendar(userId, id);
        return ResponseBuilder.ok(null, "Calendar deleted successfully");
    }
}
