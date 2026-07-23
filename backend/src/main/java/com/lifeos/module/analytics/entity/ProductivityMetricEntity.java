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
 * JPA Entity for analytics.productivity_metrics table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "analytics", name = "productivity_metrics")
public class ProductivityMetricEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "metric_date", nullable = false)
    private LocalDate metricDate;

    @Column(name = "tasks_completed_count", nullable = false)
    private Integer tasksCompletedCount = 0;

    @Column(name = "habits_completed_count", nullable = false)
    private Integer habitsCompletedCount = 0;

    @Column(name = "focus_minutes_total", nullable = false)
    private Integer focusMinutesTotal = 0;

    @Column(name = "productivity_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal productivityScore = BigDecimal.ZERO;
}
