package com.lifeos.module.analytics.dto;

import java.util.UUID;

/**
 * Record DTO for Dashboard response.
 */
public record DashboardResponse(
        UUID id,
        UUID userId,
        String dashboardName,
        String layoutConfigJsonb,
        Boolean isDefault
) {
}
