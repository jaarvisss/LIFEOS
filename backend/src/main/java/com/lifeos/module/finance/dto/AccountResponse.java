package com.lifeos.module.finance.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Record DTO for Account response.
 */
public record AccountResponse(
        UUID id,
        UUID userId,
        String name,
        String accountType,
        String currency,
        BigDecimal currentBalance
) {
}
