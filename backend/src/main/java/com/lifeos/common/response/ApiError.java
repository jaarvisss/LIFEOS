package com.lifeos.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lifeos.common.enums.ErrorCategory;
import com.lifeos.common.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Standardized ApiError container for RFC7807 problem details and application error responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private String code;
    private ErrorCategory category;
    private String message;
    private String path;

    @Builder.Default
    private Instant timestamp = Instant.now();

    private String correlationId;

    @Builder.Default
    private List<FieldErrorItem> fieldErrors = new ArrayList<>();

    public static ApiError of(ErrorCode errorCode, String message, String path, String correlationId) {
        return ApiError.builder()
                .code(errorCode.getCode())
                .category(errorCode.getCategory())
                .message(message != null ? message : errorCode.getDefaultMessage())
                .path(path)
                .timestamp(Instant.now())
                .correlationId(correlationId)
                .build();
    }
}
