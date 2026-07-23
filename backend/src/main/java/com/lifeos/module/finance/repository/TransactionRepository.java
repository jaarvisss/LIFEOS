package com.lifeos.module.finance.repository;

import com.lifeos.module.finance.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for finance.transactions table.
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    Page<TransactionEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Page<TransactionEntity> findByUserIdAndAccountIdAndIsDeletedFalse(UUID userId, UUID accountId, Pageable pageable);

    Optional<TransactionEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
