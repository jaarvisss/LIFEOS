package com.lifeos.common.request;

import com.lifeos.common.constants.CommonConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Standardized Pagination Request DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

    @Min(value = 0, message = "Page index must not be less than zero")
    @Builder.Default
    private int page = CommonConstants.DEFAULT_PAGE_NUMBER;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = CommonConstants.MAX_PAGE_SIZE, message = "Page size must not exceed " + CommonConstants.MAX_PAGE_SIZE)
    @Builder.Default
    private int size = CommonConstants.DEFAULT_PAGE_SIZE;

    @Builder.Default
    private String sortBy = CommonConstants.DEFAULT_SORT_BY;

    @Builder.Default
    private String sortDirection = CommonConstants.DEFAULT_SORT_DIRECTION;

    public Pageable toPageable() {
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortDirection);
        } catch (Exception ex) {
            direction = Sort.Direction.DESC;
        }

        String field = sortBy;
        if (field == null || field.isBlank() || "string".equalsIgnoreCase(field)) {
            field = "createdAt";
        }

        try {
            return PageRequest.of(page, size, Sort.by(direction, field));
        } catch (Exception ex) {
            return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        }
    }
}
