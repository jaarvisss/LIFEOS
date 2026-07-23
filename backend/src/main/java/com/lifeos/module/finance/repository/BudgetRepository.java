package com.lifeos.module.finance.repository;

import com.lifeos.module.finance.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for finance.budgets table.
 */
@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, UUID> {

    List<BudgetEntity> findByUserIdAndIsDeletedFalse(UUID userId);

    Optional<BudgetEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
