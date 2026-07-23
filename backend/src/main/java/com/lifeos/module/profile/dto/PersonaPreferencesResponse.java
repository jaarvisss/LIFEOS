package com.lifeos.module.profile.dto;

import java.util.UUID;

/**
 * Record DTO for Persona Preferences response.
 */
public record PersonaPreferencesResponse(
        UUID id,
        UUID userId,
        String aiTone,
        String verbosityLevel,
        Boolean proactiveSuggestions,
        String preferredAiProvider
) {
}
