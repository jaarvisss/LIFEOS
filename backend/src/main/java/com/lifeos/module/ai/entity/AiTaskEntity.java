package com.lifeos.module.ai.entity;

import com.lifeos.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(name = "task_name", nullable = false, length = 150)
    private String taskName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "parameters_jsonb", columnDefinition = "jsonb")
    private String parametersJsonb;

    @Builder.Default
    @Column(name = "status", nullable = false, length = 50)
    private String status = "PENDING";
}
