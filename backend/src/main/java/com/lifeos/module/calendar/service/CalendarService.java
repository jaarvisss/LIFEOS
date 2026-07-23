package com.lifeos.module.calendar.service;

import com.lifeos.module.calendar.dto.CalendarRequest;
import com.lifeos.module.calendar.dto.CalendarResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Calendar operations.
 */
public interface CalendarService {

    CalendarResponse createCalendar(UUID userId, CalendarRequest request);

    CalendarResponse getCalendarById(UUID userId, UUID id);

    List<CalendarResponse> getCalendarsByUserId(UUID userId);

    CalendarResponse updateCalendar(UUID userId, UUID id, CalendarRequest request);

    void deleteCalendar(UUID userId, UUID id);
}
