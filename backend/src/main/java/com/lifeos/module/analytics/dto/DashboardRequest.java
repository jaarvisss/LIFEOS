package com.lifeos.module.analytics.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for Dashboard layout request.
 */
public record DashboardRequest(
        @NotBlank(message = "Dashboard name is required")
        String dashboardName,

        String layoutConfigJsonb,
        Boolean isDefault
) {
}
