package com.lifeos.module.relationship.entity;

import com.lifeos.common.base.BaseEntity;
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
 * JPA Entity for relationship.milestones table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "relationship", name = "milestones")
public class MilestoneEntity extends BaseEntity {

    @Column(name = "relationship_id", nullable = false)
    private UUID relationshipId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "recurring_annual", nullable = false)
    private Boolean recurringAnnual = true;
}
