package com.lifeos.module.event.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.event.dto.EventRequest;
import com.lifeos.module.event.dto.EventResponse;
import com.lifeos.module.event.dto.ReminderRequest;
import com.lifeos.module.event.dto.ReminderResponse;
import com.lifeos.module.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Event Scheduling and Reminders.
 */
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Tag(name = "Event Scheduling", description = "Endpoints for creating and managing scheduled events, reminders, and availability")
public class EventController {

    private final EventService eventService;

    @PostMapping
    @Operation(summary = "Create Event", description = "Schedules a new event in a calendar.")
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@Valid @RequestBody EventRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(eventService.createEvent(userId, request), "Event created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Event by ID", description = "Retrieves event details.")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(eventService.getEventById(userId, id));
    }

    @GetMapping("/calendar/{calendarId}")
    @Operation(summary = "List Calendar Events", description = "Retrieves paginated events for a calendar.")
    public ResponseEntity<ApiResponse<PagedResponse<EventResponse>>> getEventsByCalendar(
            @PathVariable UUID calendarId,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(eventService.getEventsByCalendar(userId, calendarId, paginationRequest));
    }

    @GetMapping("/range")
    @Operation(summary = "Get Events in Time Range", description = "Retrieves all events in a calendar between start and end timestamps.")
    public ResponseEntity<ApiResponse<List<EventResponse>>> getEventsInRange(
            @RequestParam UUID calendarId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(eventService.getEventsInTimeRange(userId, calendarId, start, end));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Event", description = "Updates event attributes.")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(
            @PathVariable UUID id,
            @Valid @RequestBody EventRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(eventService.updateEvent(userId, id, request), "Event updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Event", description = "Soft-deletes an event.")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        eventService.deleteEvent(userId, id);
        return ResponseBuilder.ok(null, "Event deleted successfully");
    }

    @PostMapping("/reminders")
    @Operation(summary = "Add Reminder", description = "Attaches a reminder alert to an event.")
    public ResponseEntity<ApiResponse<ReminderResponse>> addReminder(@Valid @RequestBody ReminderRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(eventService.addReminder(userId, request), "Reminder created successfully");
    }

    @GetMapping("/{id}/reminders")
    @Operation(summary = "Get Event Reminders", description = "Retrieves all reminders for an event.")
    public ResponseEntity<ApiResponse<List<ReminderResponse>>> getReminders(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(eventService.getReminders(userId, id));
    }
}
