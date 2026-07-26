package com.lifeos.domain.memory.domain.repository;

import com.lifeos.common.response.PagedResponse;
import com.lifeos.domain.memory.domain.aggregate.Memory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Domain Repository Interface for Memory Aggregate Root.
 */
public interface MemoryDomainRepository {

    Memory save(Memory memory);

    Optional<Memory> findByIdAndUserId(UUID id, UUID userId);

    PagedResponse<Memory> findByUserId(UUID userId, String status, String type, int page, int size);

    List<Memory> findFavoritesByUserId(UUID userId);

    List<Memory> findPinnedByUserId(UUID userId);

    List<Memory> findRecentlyAccessedByUserId(UUID userId, int limit);

    List<Memory> searchByUserIdAndQuery(UUID userId, String query);

    long countPinnedByUserId(UUID userId);

    long countFavoritesByUserId(UUID userId);

    void delete(Memory memory);
}
