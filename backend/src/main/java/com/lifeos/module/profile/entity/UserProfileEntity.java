package com.lifeos.module.profile.entity;

import com.lifeos.common.base.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

/**
 * JPA Entity for profile.user_profiles table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "profile", name = "user_profiles")
public class UserProfileEntity extends BaseSoftDeleteEntity {

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "display_name", length = 150)
    private String displayName;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "time_zone", nullable = false, length = 100)
    private String timeZone = "UTC";

    @Column(name = "locale", nullable = false, length = 10)
    private String locale = "en_US";

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;
}
