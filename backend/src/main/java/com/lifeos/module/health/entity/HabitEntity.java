package com.lifeos.module.health.entity;

import com.lifeos.common.base.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * JPA Entity for health.habits table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "health", name = "habits")
public class HabitEntity extends BaseSoftDeleteEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Column(name = "target_frequency", nullable = false, length = 50)
    private String targetFrequency = "DAILY";

    @Builder.Default
    @Column(name = "current_streak_count", nullable = false)
    private Integer currentStreakCount = 0;

    @Builder.Default
    @Column(name = "best_streak_count", nullable = false)
    private Integer bestStreakCount = 0;
}
