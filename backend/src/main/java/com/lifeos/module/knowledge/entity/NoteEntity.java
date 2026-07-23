package com.lifeos.module.knowledge.entity;

import com.lifeos.common.base.BaseSoftDeleteEntity;
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
 * JPA Entity for knowledge.notes table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "knowledge", name = "notes")
public class NoteEntity extends BaseSoftDeleteEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "notebook_id")
    private UUID notebookId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_favorite", nullable = false)
    private Boolean isFavorite = false;
}
