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

import java.util.UUID;

/**
 * JPA Entity for analytics.dashboards table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "analytics", name = "dashboards")
public class DashboardEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "dashboard_name", nullable = false, length = 150)
    private String dashboardName;

    @Column(name = "layout_config_jsonb", nullable = false, columnDefinition = "jsonb")
    private String layoutConfigJsonb = "{}";

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
}
