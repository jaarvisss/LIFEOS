package com.lifeos.common.exception;

import com.lifeos.common.enums.ErrorCode;

/**
 * Exception thrown when a state conflict or duplicate resource exists.
 */
public class ConflictException extends BaseException {

    public ConflictException(String message) {
        super(ErrorCode.RESOURCE_ALREADY_EXISTS, message);
    }

    public ConflictException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
