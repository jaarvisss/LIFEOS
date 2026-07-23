package com.lifeos.module.people.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Record DTO for Contact creation/update payload.
 */
public record ContactRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        String lastName,
        String email,
        String phoneNumber,
        String company,
        String jobTitle,
        LocalDate dateOfBirth,
        String notes
) {
}
