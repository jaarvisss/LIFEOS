package com.lifeos.common.util;

import com.lifeos.common.exception.ValidationException;
import com.lifeos.common.response.FieldErrorItem;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enterprise Programmatic Validation Utility wrapper around Jakarta Validation API.
 */
public final class ValidationUtils {

    private static final Validator VALIDATOR;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            VALIDATOR = factory.getValidator();
        }
    }

    private ValidationUtils() {
    }

    public static <T> void validate(T object, Class<?>... groups) {
        if (object == null) {
            throw new ValidationException("Target validation object must not be null");
        }
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(object, groups);
        if (!violations.isEmpty()) {
            List<FieldErrorItem> errors = violations.stream()
                    .map(v -> FieldErrorItem.builder()
                            .field(v.getPropertyPath().toString())
                            .rejectedValue(v.getInvalidValue())
                            .message(v.getMessage())
                            .build())
                    .collect(Collectors.toList());
            throw new ValidationException("Validation failed for object: " + object.getClass().getSimpleName(), errors);
        }
    }
}
