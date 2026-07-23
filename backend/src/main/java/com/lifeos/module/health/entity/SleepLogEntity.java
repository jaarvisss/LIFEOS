package com.lifeos.module.health.entity;

import com.lifeos.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity for health.sleep_logs table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "health", name = "sleep_logs")
public class SleepLogEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "sleep_start", nullable = false)
    private Instant sleepStart;

    @Column(name = "sleep_end", nullable = false)
    private Instant sleepEnd;

    @Column(name = "quality_score")
    private Integer qualityScore;
}
