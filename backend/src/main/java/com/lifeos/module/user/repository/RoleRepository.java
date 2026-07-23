package com.lifeos.module.user.repository;

import com.lifeos.module.user.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for identity.roles table.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByRoleCode(String roleCode);

    boolean existsByRoleCode(String roleCode);
}
