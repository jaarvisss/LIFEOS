package com.lifeos.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Detailed record representing an individual field-level validation error.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorItem {
    private String field;
    private Object rejectedValue;
    private String message;
}
