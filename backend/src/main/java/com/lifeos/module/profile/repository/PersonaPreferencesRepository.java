package com.lifeos.module.profile.repository;

import com.lifeos.module.profile.entity.PersonaPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for profile.persona_preferences table.
 */
@Repository
public interface PersonaPreferencesRepository extends JpaRepository<PersonaPreferencesEntity, UUID> {

    Optional<PersonaPreferencesEntity> findByUserId(UUID userId);
}
