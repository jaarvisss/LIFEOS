package com.lifeos.module.people.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Communication Log creation payload.
 */
public record CommunicationLogRequest(
        @NotNull(message = "Contact ID is required")
        UUID contactId,

        @NotBlank(message = "Channel is required")
        String channel,

        @NotBlank(message = "Summary is required")
        String summary,

        Instant interactionDate
) {
}
