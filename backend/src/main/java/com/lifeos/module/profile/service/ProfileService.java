package com.lifeos.module.profile.service;

import com.lifeos.module.profile.dto.PersonaPreferencesRequest;
import com.lifeos.module.profile.dto.PersonaPreferencesResponse;
import com.lifeos.module.profile.dto.UserProfileRequest;
import com.lifeos.module.profile.dto.UserProfileResponse;
import com.lifeos.module.profile.dto.WorkLifeBoundariesRequest;
import com.lifeos.module.profile.dto.WorkLifeBoundariesResponse;

import java.util.UUID;

/**
 * Service Interface for Personal Profile, Persona Preferences, and Work-Life Boundaries operations.
 */
public interface ProfileService {

    UserProfileResponse getProfileByUserId(UUID userId);

    UserProfileResponse createOrUpdateProfile(UUID userId, UserProfileRequest request);

    PersonaPreferencesResponse getPersonaPreferencesByUserId(UUID userId);

    PersonaPreferencesResponse updatePersonaPreferences(UUID userId, PersonaPreferencesRequest request);

    WorkLifeBoundariesResponse getWorkLifeBoundariesByUserId(UUID userId);

    WorkLifeBoundariesResponse updateWorkLifeBoundaries(UUID userId, WorkLifeBoundariesRequest request);
}
