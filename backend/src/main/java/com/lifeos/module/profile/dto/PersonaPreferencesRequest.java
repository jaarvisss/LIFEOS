package com.lifeos.module.profile.dto;

/**
 * Record DTO for updating persona preferences.
 */
public record PersonaPreferencesRequest(
        String aiTone,
        String verbosityLevel,
        Boolean proactiveSuggestions,
        String preferredAiProvider
) {
}
