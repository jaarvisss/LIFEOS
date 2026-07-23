package com.lifeos.common.exception;

import com.lifeos.common.enums.ErrorCode;

/**
 * Exception thrown when business logic constraints or rules are violated.
 */
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
