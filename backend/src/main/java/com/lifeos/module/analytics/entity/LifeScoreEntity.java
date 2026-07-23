package com.lifeos.module.analytics.entity;

import com.lifeos.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * JPA Entity for analytics.life_scores table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "analytics", name = "life_scores")
public class LifeScoreEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "score_date", nullable = false)
    private LocalDate scoreDate = LocalDate.now();

    @Column(name = "overall_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal overallScore;

    @Column(name = "health_subscore", nullable = false, precision = 5, scale = 2)
    private BigDecimal healthSubscore;

    @Column(name = "finance_subscore", nullable = false, precision = 5, scale = 2)
    private BigDecimal financeSubscore;

    @Column(name = "productivity_subscore", nullable = false, precision = 5, scale = 2)
    private BigDecimal productivitySubscore;

    @Column(name = "relationship_subscore", nullable = false, precision = 5, scale = 2)
    private BigDecimal relationshipSubscore;
}
