package com.lifeos.module.finance.repository;

import com.lifeos.module.finance.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for finance.accounts table.
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    List<AccountEntity> findByUserIdAndIsDeletedFalse(UUID userId);

    Optional<AccountEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
