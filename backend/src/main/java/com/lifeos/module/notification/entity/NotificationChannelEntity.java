package com.lifeos.module.notification.entity;

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
 * JPA Entity for notification.notification_channels table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "notification", name = "notification_channels")
public class NotificationChannelEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "channel_type", nullable = false, length = 50)
    private String channelType;

    @Column(name = "destination", nullable = false, length = 255)
    private String destination;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;
}
