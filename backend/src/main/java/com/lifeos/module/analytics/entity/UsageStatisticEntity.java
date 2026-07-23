package com.lifeos.module.analytics.entity;

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

import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity for analytics.usage_statistics table.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "analytics", name = "usage_statistics")
public class UsageStatisticEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "feature_name", nullable = false, length = 100)
    private String featureName;

    @Column(name = "action_count", nullable = false)
    private Integer actionCount = 1;

    @Column(name = "last_used_at", nullable = false)
    private Instant lastUsedAt = Instant.now();

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUIDUtils.generate();
        }
    }
}
