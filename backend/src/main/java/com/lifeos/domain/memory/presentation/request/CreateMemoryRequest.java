package com.lifeos.domain.memory.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for Creating Memory.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemoryRequest {

    @NotBlank(message = "Memory title is required")
    @Size(max = 200, message = "Memory title cannot exceed 200 characters")
    private String title;

    @NotBlank(message = "Memory content is required")
    @Size(max = 50000, message = "Memory content cannot exceed 50,000 characters")
    private String content;

    @Size(max = 1000, message = "Memory summary cannot exceed 1000 characters")
    private String summary;

    private String memoryType;
    private String importanceLevel;
    private String sourceType;
    private UUID lifeProfileId;
    private String language;
    private Double confidenceScore;
    private String metadata;
}
