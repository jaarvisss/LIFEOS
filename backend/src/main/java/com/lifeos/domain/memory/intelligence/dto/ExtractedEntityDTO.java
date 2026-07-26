package com.lifeos.domain.memory.intelligence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Extracted Named Entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtractedEntityDTO {
    private String type;
    private String value;
    private double confidence;
}
