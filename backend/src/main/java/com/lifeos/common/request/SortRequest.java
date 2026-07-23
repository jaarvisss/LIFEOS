package com.lifeos.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

/**
 * Standardized Sorting Request DTO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortRequest {

    private String field;

    @Builder.Default
    private String direction = "ASC";

    public Sort.Order toOrder() {
        return new Sort.Order(Sort.Direction.fromString(direction), field);
    }
}
