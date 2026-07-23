package com.lifeos.module.profile.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.profile.dto.PersonaPreferencesRequest;
import com.lifeos.module.profile.dto.PersonaPreferencesResponse;
import com.lifeos.module.profile.dto.UserProfileRequest;
import com.lifeos.module.profile.dto.UserProfileResponse;
import com.lifeos.module.profile.dto.WorkLifeBoundariesRequest;
import com.lifeos.module.profile.dto.WorkLifeBoundariesResponse;
import com.lifeos.module.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller for Profile, Persona Preferences, and Work-Life Boundaries.
 */
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Tag(name = "Profile & Settings", description = "Endpoints for personal profile details, AI persona preferences, and work-life boundaries")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    @Operation(summary = "Get Profile", description = "Retrieves profile information for the authenticated user.")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(profileService.getProfileByUserId(userId));
    }

    @PutMapping
    @Operation(summary = "Update Profile", description = "Creates or updates profile details.")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(@Valid @RequestBody UserProfileRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(profileService.createOrUpdateProfile(userId, request), "Profile updated successfully");
    }

    @GetMapping("/persona")
    @Operation(summary = "Get Persona Preferences", description = "Retrieves AI persona preferences.")
    public ResponseEntity<ApiResponse<PersonaPreferencesResponse>> getPersonaPreferences() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(profileService.getPersonaPreferencesByUserId(userId));
    }

    @PutMapping("/persona")
    @Operation(summary = "Update Persona Preferences", description = "Updates AI persona preferences.")
    public ResponseEntity<ApiResponse<PersonaPreferencesResponse>> updatePersonaPreferences(@RequestBody PersonaPreferencesRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(profileService.updatePersonaPreferences(userId, request), "Persona preferences updated");
    }

    @GetMapping("/boundaries")
    @Operation(summary = "Get Work-Life Boundaries", description = "Retrieves work-life scheduling boundaries.")
    public ResponseEntity<ApiResponse<WorkLifeBoundariesResponse>> getWorkLifeBoundaries() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(profileService.getWorkLifeBoundariesByUserId(userId));
    }

    @PutMapping("/boundaries")
    @Operation(summary = "Update Work-Life Boundaries", description = "Updates work-life scheduling boundaries.")
    public ResponseEntity<ApiResponse<WorkLifeBoundariesResponse>> updateWorkLifeBoundaries(@RequestBody WorkLifeBoundariesRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(profileService.updateWorkLifeBoundaries(userId, request), "Work-life boundaries updated");
    }
}
