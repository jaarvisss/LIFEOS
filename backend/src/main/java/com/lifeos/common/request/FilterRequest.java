package com.lifeos.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Generic Filter Specification Request model for search queries.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest {

    private String field;
    private String operator;
    private Object value;
    private List<Object> values;
}
