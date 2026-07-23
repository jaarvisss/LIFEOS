package com.lifeos.module.finance.entity;

import com.lifeos.common.base.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity for finance.transactions table.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "finance", name = "transactions")
public class TransactionEntity extends BaseSoftDeleteEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "budget_id")
    private UUID budgetId;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "USD";

    @Column(name = "transaction_type", nullable = false, length = 50)
    private String transactionType;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @Column(name = "transaction_timestamp", nullable = false)
    private Instant transactionTimestamp = Instant.now();

    @Column(name = "merchant_name", length = 255)
    private String merchantName;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
