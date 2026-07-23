package com.lifeos.common.exception;

import com.lifeos.common.enums.ErrorCode;

/**
 * Exception thrown when authenticated user lacks permissions for an operation.
 */
public class ForbiddenException extends BaseException {

    public ForbiddenException(String message) {
        super(ErrorCode.FORBIDDEN, message);
    }

    public ForbiddenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
