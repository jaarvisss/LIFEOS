package com.lifeos.module.finance.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Record DTO for Budget response.
 */
public record BudgetResponse(
        UUID id,
        UUID userId,
        String name,
        String category,
        BigDecimal allocatedAmount,
        String currency,
        String periodType
) {
}
