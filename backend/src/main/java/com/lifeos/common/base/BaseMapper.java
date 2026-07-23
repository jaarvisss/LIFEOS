package com.lifeos.common.base;

import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Generic Base MapStruct Mapper interface contract.
 *
 * @param <E> Entity domain model
 * @param <D> Response DTO
 * @param <C> Create Request DTO
 * @param <U> Update Request DTO
 */
public interface BaseMapper<E, D, C, U> {

    D toDto(E entity);

    E toEntity(C createRequest);

    List<D> toDtoList(List<E> entityList);

    List<E> toEntityList(List<C> createRequestList);

    void updateEntityFromDto(U updateRequest, @MappingTarget E entity);
}
