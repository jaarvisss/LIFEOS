package com.lifeos.module.ai.entity;

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
 * JPA Entity for ai.ai_tasks table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "ai", name = "ai_tasks")
public class AiTaskEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "task_name", nullable = false, length = 255)
    private String taskName;

    @Column(name = "parameters_jsonb", columnDefinition = "jsonb")
    private String parametersJsonb;

    @Column(name = "status", nullable = false, length = 50)
    private String status = "PENDING";

    @Column(name = "result_summary", columnDefinition = "TEXT")
    private String resultSummary;

    @Column(name = "scheduled_for")
    private Instant scheduledFor;

    @Column(name = "executed_at")
    private Instant executedAt;
}
