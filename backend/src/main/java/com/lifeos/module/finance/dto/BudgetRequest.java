package com.lifeos.module.finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Record DTO for Budget creation request.
 */
public record BudgetRequest(
        @NotBlank(message = "Budget name is required")
        String name,

        @NotBlank(message = "Category is required")
        String category,

        @NotNull(message = "Allocated amount is required")
        BigDecimal allocatedAmount,

        String currency,
        String periodType
) {
}
