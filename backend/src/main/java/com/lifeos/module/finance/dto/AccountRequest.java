package com.lifeos.module.finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Record DTO for Account creation/update request.
 */
public record AccountRequest(
        @NotBlank(message = "Account name is required")
        String name,

        @NotBlank(message = "Account type is required")
        String accountType,

        String currency,

        @NotNull(message = "Current balance is required")
        BigDecimal currentBalance
) {
}
