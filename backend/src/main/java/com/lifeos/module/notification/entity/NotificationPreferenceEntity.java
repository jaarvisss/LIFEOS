package com.lifeos.module.notification.entity;

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

import java.util.UUID;

/**
 * JPA Entity for notification.notification_preferences table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "notification", name = "notification_preferences")
public class NotificationPreferenceEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Builder.Default
    @Column(name = "email_enabled", nullable = false)
    private Boolean emailEnabled = true;

    @Builder.Default
    @Column(name = "push_enabled", nullable = false)
    private Boolean pushEnabled = true;

    @Builder.Default
    @Column(name = "whatsapp_enabled", nullable = false)
    private Boolean whatsappEnabled = false;

    @Builder.Default
    @Column(name = "sms_enabled", nullable = false)
    private Boolean smsEnabled = false;
}
