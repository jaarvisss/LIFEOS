package com.lifeos.module.profile.entity;

import com.lifeos.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * JPA Entity for profile.persona_preferences table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "profile", name = "persona_preferences")
public class PersonaPreferencesEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "ai_tone", nullable = false, length = 50)
    private String aiTone = "BALANCED";

    @Column(name = "verbosity_level", nullable = false, length = 50)
    private String verbosityLevel = "CONCISE";

    @Column(name = "proactive_suggestions", nullable = false)
    private Boolean proactiveSuggestions = true;

    @Column(name = "preferred_ai_provider", nullable = false, length = 50)
    private String preferredAiProvider = "GEMINI_PRO";
}
