package com.lifeos.domain.memory.semantic.service;

import com.lifeos.domain.memory.semantic.dto.TimelineEventDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Memory Timeline generation.
 */
public interface MemoryTimelineService {

    List<TimelineEventDTO> getTimeline(UUID userId, String timeframe); // daily, weekly, monthly, all
}
