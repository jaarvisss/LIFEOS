package com.lifeos.module.event.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.calendar.entity.CalendarEntity;
import com.lifeos.module.calendar.repository.CalendarRepository;
import com.lifeos.module.event.dto.EventRequest;
import com.lifeos.module.event.dto.EventResponse;
import com.lifeos.module.event.dto.ReminderRequest;
import com.lifeos.module.event.dto.ReminderResponse;
import com.lifeos.module.event.entity.EventEntity;
import com.lifeos.module.event.entity.ReminderEntity;
import com.lifeos.module.event.mapper.EventMapper;
import com.lifeos.module.event.repository.EventRepository;
import com.lifeos.module.event.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of EventService interface.
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CalendarRepository calendarRepository;
    private final ReminderRepository reminderRepository;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventResponse createEvent(UUID userId, EventRequest request) {
        UUID calendarId = request.calendarId();
        if (calendarId == null) {
            List<CalendarEntity> calendars = calendarRepository.findByUserIdAndIsDeletedFalse(userId);
            if (calendars.isEmpty()) {
                CalendarEntity defaultCal = calendarRepository.save(CalendarEntity.builder()
                        .userId(userId)
                        .name("Personal Calendar")
                        .description("Default personal calendar")
                        .colorCode("#3B82F6")
                        .isDefault(true)
                        .build());
                calendarId = defaultCal.getId();
            } else {
                calendarId = calendars.get(0).getId();
            }
        } else {
            calendarRepository.findByIdAndUserIdAndIsDeletedFalse(calendarId, userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Calendar", "id", request.calendarId()));
        }

        EventEntity entity = eventMapper.toEntity(request);
        entity.setCalendarId(calendarId);
        if (entity.getIsAllDay() == null) {
            entity.setIsAllDay(false);
        }
        if (entity.getEventStatus() == null || entity.getEventStatus().isBlank()) {
            entity.setEventStatus("CONFIRMED");
        }
        entity = eventRepository.save(entity);
        return eventMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponse getEventById(UUID userId, UUID id) {
        EventEntity entity = eventRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        return eventMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<EventResponse> getEventsByCalendar(UUID userId, UUID calendarId, PaginationRequest paginationRequest) {
        calendarRepository.findByIdAndUserIdAndIsDeletedFalse(calendarId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar", "id", calendarId));

        Page<EventEntity> page = eventRepository.findByCalendarIdAndIsDeletedFalse(calendarId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(eventMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getEventsInTimeRange(UUID userId, UUID calendarId, Instant start, Instant end) {
        calendarRepository.findByIdAndUserIdAndIsDeletedFalse(calendarId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar", "id", calendarId));

        return eventMapper.toDtoList(eventRepository.findEventsInTimeRange(calendarId, start, end));
    }

    @Override
    @Transactional
    public EventResponse updateEvent(UUID userId, UUID id, EventRequest request) {
        EventEntity entity = eventRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));

        eventMapper.updateEntityFromDto(request, entity);
        if (entity.getIsAllDay() == null) {
            entity.setIsAllDay(false);
        }
        if (entity.getEventStatus() == null || entity.getEventStatus().isBlank()) {
            entity.setEventStatus("CONFIRMED");
        }
        entity = eventRepository.save(entity);
        return eventMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteEvent(UUID userId, UUID id) {
        EventEntity entity = eventRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        entity.markDeleted();
        eventRepository.save(entity);
    }

    @Override
    @Transactional
    public ReminderResponse addReminder(UUID userId, ReminderRequest request) {
        eventRepository.findByIdAndIsDeletedFalse(request.eventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", request.eventId()));

        ReminderEntity reminder = eventMapper.toEntity(request);
        reminder = reminderRepository.save(reminder);
        return eventMapper.toDto(reminder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReminderResponse> getReminders(UUID userId, UUID eventId) {
        eventRepository.findByIdAndIsDeletedFalse(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));

        return eventMapper.toReminderDtoList(reminderRepository.findByEventId(eventId));
    }
}
