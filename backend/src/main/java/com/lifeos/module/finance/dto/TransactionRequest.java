package com.lifeos.module.finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Transaction creation request.
 */
public record TransactionRequest(
        @NotNull(message = "Account ID is required")
        UUID accountId,

        UUID budgetId,

        @NotNull(message = "Amount is required")
        BigDecimal amount,

        String currency,

        @NotBlank(message = "Transaction type is required")
        String transactionType,

        @NotBlank(message = "Category is required")
        String category,

        Instant transactionTimestamp,
        String merchantName,
        String notes
) {
}
