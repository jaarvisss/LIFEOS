package com.lifeos.domain.memory.infrastructure.repository;

import com.lifeos.domain.memory.infrastructure.entity.MemoryDomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for MemoryDomainEntity.
 */
@Repository
public interface JpaMemoryDomainRepository extends JpaRepository<MemoryDomainEntity, UUID> {

    Optional<MemoryDomainEntity> findByIdAndUserIdAndDeletedFalse(UUID id, UUID userId);

    Page<MemoryDomainEntity> findByUserIdAndDeletedFalse(UUID userId, Pageable pageable);

    Page<MemoryDomainEntity> findByUserIdAndMemoryStatusAndDeletedFalse(UUID userId, String memoryStatus, Pageable pageable);

    Page<MemoryDomainEntity> findByUserIdAndMemoryTypeAndDeletedFalse(UUID userId, String memoryType, Pageable pageable);

    Page<MemoryDomainEntity> findByUserIdAndMemoryStatusAndMemoryTypeAndDeletedFalse(UUID userId, String memoryStatus, String memoryType, Pageable pageable);

    List<MemoryDomainEntity> findByUserIdAndFavoriteTrueAndDeletedFalseOrderByUpdatedAtDesc(UUID userId);

    List<MemoryDomainEntity> findByUserIdAndPinnedTrueAndDeletedFalseOrderByUpdatedAtDesc(UUID userId);

    List<MemoryDomainEntity> findTop10ByUserIdAndDeletedFalseOrderByLastAccessedAtDesc(UUID userId);

    @Query("SELECT m FROM MemoryDomainEntity m WHERE m.userId = :userId AND m.deleted = false AND (LOWER(m.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(m.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<MemoryDomainEntity> searchByUserIdAndQuery(@Param("userId") UUID userId, @Param("query") String query);

    long countByUserIdAndPinnedTrueAndDeletedFalse(UUID userId);

    long countByUserIdAndFavoriteTrueAndDeletedFalse(UUID userId);
}
