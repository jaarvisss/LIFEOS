package com.lifeos.common.exception;

import com.lifeos.common.enums.ErrorCode;
import com.lifeos.common.response.FieldErrorItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception thrown for field-level or programmatic validation errors.
 */
@Getter
public class ValidationException extends BaseException {

    private final List<FieldErrorItem> fieldErrors;

    public ValidationException(String message) {
        super(ErrorCode.VALIDATION_FAILED, message);
        this.fieldErrors = new ArrayList<>();
    }

    public ValidationException(String message, List<FieldErrorItem> fieldErrors) {
        super(ErrorCode.VALIDATION_FAILED, message);
        this.fieldErrors = fieldErrors != null ? fieldErrors : new ArrayList<>();
    }
}
