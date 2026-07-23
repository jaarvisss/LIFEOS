package com.lifeos.common.exception;

import com.lifeos.common.enums.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * Enterprise Base Runtime Exception.
 */
@Getter
public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;
    private final Instant timestamp;

    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.timestamp = Instant.now();
    }

    protected BaseException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.timestamp = Instant.now();
    }

    protected BaseException(ErrorCode errorCode, String customMessage, Throwable cause) {
        super(customMessage, cause);
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.timestamp = Instant.now();
    }
}
