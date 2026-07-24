package com.lifeos.module.audit.entity;

import com.lifeos.common.util.UUIDUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity for audit.entity_history table.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "audit", name = "entity_history")
public class EntityHistoryEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "entity_name", nullable = false, length = 100)
    private String entityName;

    @Column(name = "entity_id", nullable = false)
    private UUID entityId;

    @Column(name = "version_number", nullable = false)
    private Long versionNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "snapshot_jsonb", nullable = false, columnDefinition = "jsonb")
    private String snapshotJsonb;

    @Column(name = "changed_by", nullable = false)
    private UUID changedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUIDUtils.generate();
        }
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }
}
