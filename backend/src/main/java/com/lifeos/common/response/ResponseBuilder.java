package com.lifeos.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Enterprise Fluent Response Builder helper for ResponseEntity<ApiResponse<T>>.
 */
public final class ResponseBuilder {

    private ResponseBuilder() {
        // Private constructor
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        return ResponseEntity.ok(ApiResponse.success(data, message));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(data, message));
    }

    public static <T> ResponseEntity<ApiResponse<T>> accepted(T data, String message) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.success(data, message));
    }

    public static ResponseEntity<ApiResponse<Void>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(ApiError apiError, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.error(apiError, status.value()));
    }
}
