package com.lifeos.module.profile.service;

import com.lifeos.module.profile.dto.PersonaPreferencesRequest;
import com.lifeos.module.profile.dto.PersonaPreferencesResponse;
import com.lifeos.module.profile.dto.UserProfileRequest;
import com.lifeos.module.profile.dto.UserProfileResponse;
import com.lifeos.module.profile.dto.WorkLifeBoundariesRequest;
import com.lifeos.module.profile.dto.WorkLifeBoundariesResponse;
import com.lifeos.module.profile.entity.PersonaPreferencesEntity;
import com.lifeos.module.profile.entity.UserProfileEntity;
import com.lifeos.module.profile.entity.WorkLifeBoundariesEntity;
import com.lifeos.module.profile.mapper.ProfileMapper;
import com.lifeos.module.profile.repository.PersonaPreferencesRepository;
import com.lifeos.module.profile.repository.UserProfileRepository;
import com.lifeos.module.profile.repository.WorkLifeBoundariesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of ProfileService interface.
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserProfileRepository userProfileRepository;
    private final PersonaPreferencesRepository personaPreferencesRepository;
    private final WorkLifeBoundariesRepository workLifeBoundariesRepository;
    private final ProfileMapper profileMapper;

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfileByUserId(UUID userId) {
        UserProfileEntity profile = userProfileRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseGet(() -> UserProfileEntity.builder()
                        .userId(userId)
                        .firstName("User")
                        .lastName("Profile")
                        .build());
        return profileMapper.toDto(profile);
    }

    @Override
    @Transactional
    public UserProfileResponse createOrUpdateProfile(UUID userId, UserProfileRequest request) {
        UserProfileEntity profile = userProfileRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseGet(() -> UserProfileEntity.builder().userId(userId).build());

        profileMapper.updateEntityFromDto(request, profile);
        profile = userProfileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaPreferencesResponse getPersonaPreferencesByUserId(UUID userId) {
        PersonaPreferencesEntity prefs = personaPreferencesRepository.findByUserId(userId)
                .orElseGet(() -> PersonaPreferencesEntity.builder().userId(userId).build());
        return profileMapper.toDto(prefs);
    }

    @Override
    @Transactional
    public PersonaPreferencesResponse updatePersonaPreferences(UUID userId, PersonaPreferencesRequest request) {
        PersonaPreferencesEntity prefs = personaPreferencesRepository.findByUserId(userId)
                .orElseGet(() -> PersonaPreferencesEntity.builder().userId(userId).build());

        profileMapper.updateEntityFromDto(request, prefs);
        prefs = personaPreferencesRepository.save(prefs);
        return profileMapper.toDto(prefs);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkLifeBoundariesResponse getWorkLifeBoundariesByUserId(UUID userId) {
        WorkLifeBoundariesEntity boundaries = workLifeBoundariesRepository.findByUserId(userId)
                .orElseGet(() -> WorkLifeBoundariesEntity.builder().userId(userId).build());
        return profileMapper.toDto(boundaries);
    }

    @Override
    @Transactional
    public WorkLifeBoundariesResponse updateWorkLifeBoundaries(UUID userId, WorkLifeBoundariesRequest request) {
        WorkLifeBoundariesEntity boundaries = workLifeBoundariesRepository.findByUserId(userId)
                .orElseGet(() -> WorkLifeBoundariesEntity.builder().userId(userId).build());

        profileMapper.updateEntityFromDto(request, boundaries);
        boundaries = workLifeBoundariesRepository.save(boundaries);
        return profileMapper.toDto(boundaries);
    }
}
