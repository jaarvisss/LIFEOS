package com.lifeos.module.calendar.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.module.calendar.dto.CalendarRequest;
import com.lifeos.module.calendar.dto.CalendarResponse;
import com.lifeos.module.calendar.entity.CalendarEntity;
import com.lifeos.module.calendar.mapper.CalendarMapper;
import com.lifeos.module.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of CalendarService interface.
 */
@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarMapper calendarMapper;

    @Override
    @Transactional
    public CalendarResponse createCalendar(UUID userId, CalendarRequest request) {
        CalendarEntity entity = calendarMapper.toEntity(request);
        entity.setUserId(userId);
        entity = calendarRepository.save(entity);
        return calendarMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public CalendarResponse getCalendarById(UUID userId, UUID id) {
        CalendarEntity entity = calendarRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar", "id", id));
        return calendarMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalendarResponse> getCalendarsByUserId(UUID userId) {
        List<CalendarEntity> list = calendarRepository.findByUserIdAndIsDeletedFalse(userId);
        if (list.isEmpty()) {
            CalendarEntity defaultCal = CalendarEntity.builder()
                    .userId(userId)
                    .name("Personal Calendar")
                    .description("Default personal calendar")
                    .colorCode("#3B82F6")
                    .isDefault(true)
                    .build();
            list = List.of(calendarRepository.save(defaultCal));
        }
        return calendarMapper.toDtoList(list);
    }

    @Override
    @Transactional
    public CalendarResponse updateCalendar(UUID userId, UUID id, CalendarRequest request) {
        CalendarEntity entity = calendarRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar", "id", id));

        calendarMapper.updateEntityFromDto(request, entity);
        entity = calendarRepository.save(entity);
        return calendarMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void deleteCalendar(UUID userId, UUID id) {
        CalendarEntity entity = calendarRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar", "id", id));
        entity.markDeleted();
        calendarRepository.save(entity);
    }
}
