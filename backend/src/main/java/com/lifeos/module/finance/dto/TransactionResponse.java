package com.lifeos.module.finance.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Transaction response.
 */
public record TransactionResponse(
        UUID id,
        UUID userId,
        UUID accountId,
        UUID budgetId,
        BigDecimal amount,
        String currency,
        String transactionType,
        String category,
        Instant transactionTimestamp,
        String merchantName,
        String notes,
        Instant createdAt
) {
}
