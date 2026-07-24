package com.lifeos.module.user.entity;

import com.lifeos.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity for identity.roles table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "identity", name = "roles")
public class RoleEntity extends BaseEntity {

    @Column(name = "role_code", nullable = false, unique = true, length = 100)
    private String roleCode;

    @Column(name = "role_name", nullable = false, length = 150)
    private String roleName;

    @Column(name = "description", length = 500)
    private String description;

    @Builder.Default
    @Column(name = "is_system_role", nullable = false)
    private Boolean isSystemRole = true;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            schema = "identity",
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissions = new HashSet<>();
}
