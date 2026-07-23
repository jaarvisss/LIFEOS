package com.lifeos.common.base;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Base Service Contract for CRUD domain operations.
 *
 * @param <ID> Primary key identifier type
 * @param <R>  Response DTO type
 * @param <C>  Create Request DTO type
 * @param <U>  Update Request DTO type
 */
public interface BaseService<ID extends Serializable, R extends BaseDTO, C extends BaseDTO, U extends BaseDTO> {

    R findById(ID id);

    List<R> findAll();

    PagedResponse<R> findAll(PaginationRequest paginationRequest);

    R create(C createRequest);

    R update(ID id, U updateRequest);

    void delete(ID id);
}
