package com.lifeos.domain.memory.semantic.dto;

import com.lifeos.domain.memory.presentation.response.MemoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Memory Timeline Items.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimelineEventDTO {
    private LocalDateTime timestamp;
    private String eventType;
    private MemoryResponse memory;
}
