package com.lifeos.common.exception;

import com.lifeos.common.enums.ErrorCode;

/**
 * Exception thrown when authentication fails.
 */
public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
