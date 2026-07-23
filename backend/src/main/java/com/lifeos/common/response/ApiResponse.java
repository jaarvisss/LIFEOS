package com.lifeos.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lifeos.common.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.time.Instant;

/**
 * Universal Unified Enterprise API Response Wrapper.
 *
 * @param <T> Payload Data Type
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private ResponseStatus status;
    private int statusCode;
    private String message;
    private T data;
    private ApiError error;

    @Builder.Default
    private Instant timestamp = Instant.now();

    private String path;
    private String correlationId;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(ResponseStatus.SUCCESS)
                .statusCode(200)
                .message("Operation completed successfully")
                .data(data)
                .timestamp(Instant.now())
                .correlationId(MDC.get("correlationId"))
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .status(ResponseStatus.SUCCESS)
                .statusCode(200)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .correlationId(MDC.get("correlationId"))
                .build();
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return ApiResponse.<T>builder()
                .status(ResponseStatus.SUCCESS)
                .statusCode(201)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .correlationId(MDC.get("correlationId"))
                .build();
    }

    public static <T> ApiResponse<T> error(ApiError error, int statusCode) {
        return ApiResponse.<T>builder()
                .status(ResponseStatus.ERROR)
                .statusCode(statusCode)
                .message(error.getMessage())
                .error(error)
                .path(error.getPath())
                .timestamp(Instant.now())
                .correlationId(error.getCorrelationId())
                .build();
    }
}
