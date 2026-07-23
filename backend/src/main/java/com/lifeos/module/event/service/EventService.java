package com.lifeos.module.event.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.event.dto.EventRequest;
import com.lifeos.module.event.dto.EventResponse;
import com.lifeos.module.event.dto.ReminderRequest;
import com.lifeos.module.event.dto.ReminderResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Event operations.
 */
public interface EventService {

    EventResponse createEvent(UUID userId, EventRequest request);

    EventResponse getEventById(UUID userId, UUID id);

    PagedResponse<EventResponse> getEventsByCalendar(UUID userId, UUID calendarId, PaginationRequest paginationRequest);

    List<EventResponse> getEventsInTimeRange(UUID userId, UUID calendarId, Instant start, Instant end);

    EventResponse updateEvent(UUID userId, UUID id, EventRequest request);

    void deleteEvent(UUID userId, UUID id);

    ReminderResponse addReminder(UUID userId, ReminderRequest request);

    List<ReminderResponse> getReminders(UUID userId, UUID eventId);
}
