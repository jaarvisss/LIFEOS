package com.lifeos.common.exception;

import com.lifeos.common.enums.ErrorCode;

/**
 * Exception thrown for persistence layer database errors.
 */
public class DatabaseException extends BaseException {

    public DatabaseException(String message) {
        super(ErrorCode.DATABASE_ERROR, message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(ErrorCode.DATABASE_ERROR, message, cause);
    }
}
