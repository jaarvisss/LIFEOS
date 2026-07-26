package com.lifeos.domain.memory.infrastructure.repository;

import com.lifeos.common.response.PagedResponse;
import com.lifeos.domain.memory.domain.aggregate.Memory;
import com.lifeos.domain.memory.domain.repository.MemoryDomainRepository;
import com.lifeos.domain.memory.infrastructure.entity.MemoryDomainEntity;
import com.lifeos.domain.memory.infrastructure.mapper.MemoryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Infrastructure Adapter implementing MemoryDomainRepository interface.
 */
@Component
@RequiredArgsConstructor
public class MemoryDomainRepositoryImpl implements MemoryDomainRepository {

    private final JpaMemoryDomainRepository jpaRepository;

    @Override
    public Memory save(Memory memory) {
        MemoryDomainEntity entity = MemoryEntityMapper.toEntity(memory);
        MemoryDomainEntity saved = jpaRepository.save(entity);
        return MemoryEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Memory> findByIdAndUserId(UUID id, UUID userId) {
        return jpaRepository.findByIdAndUserIdAndDeletedFalse(id, userId)
                .map(MemoryEntityMapper::toDomain);
    }

    @Override
    public PagedResponse<Memory> findByUserId(UUID userId, String status, String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<MemoryDomainEntity> entityPage;

        if (status != null && !status.isBlank() && type != null && !type.isBlank()) {
            entityPage = jpaRepository.findByUserIdAndMemoryStatusAndMemoryTypeAndDeletedFalse(userId, status.toUpperCase(), type.toUpperCase(), pageable);
        } else if (status != null && !status.isBlank()) {
            entityPage = jpaRepository.findByUserIdAndMemoryStatusAndDeletedFalse(userId, status.toUpperCase(), pageable);
        } else if (type != null && !type.isBlank()) {
            entityPage = jpaRepository.findByUserIdAndMemoryTypeAndDeletedFalse(userId, type.toUpperCase(), pageable);
        } else {
            entityPage = jpaRepository.findByUserIdAndDeletedFalse(userId, pageable);
        }

        List<Memory> content = entityPage.getContent().stream()
                .map(MemoryEntityMapper::toDomain)
                .collect(Collectors.toList());

        return PagedResponse.of(content, entityPage.getNumber(), entityPage.getSize(), entityPage.getTotalElements(), entityPage.getTotalPages());
    }

    @Override
    public List<Memory> findFavoritesByUserId(UUID userId) {
        return jpaRepository.findByUserIdAndFavoriteTrueAndDeletedFalseOrderByUpdatedAtDesc(userId).stream()
                .map(MemoryEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Memory> findPinnedByUserId(UUID userId) {
        return jpaRepository.findByUserIdAndPinnedTrueAndDeletedFalseOrderByUpdatedAtDesc(userId).stream()
                .map(MemoryEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Memory> findRecentlyAccessedByUserId(UUID userId, int limit) {
        return jpaRepository.findTop10ByUserIdAndDeletedFalseOrderByLastAccessedAtDesc(userId).stream()
                .map(MemoryEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Memory> searchByUserIdAndQuery(UUID userId, String query) {
        return jpaRepository.searchByUserIdAndQuery(userId, query).stream()
                .map(MemoryEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countPinnedByUserId(UUID userId) {
        return jpaRepository.countByUserIdAndPinnedTrueAndDeletedFalse(userId);
    }

    @Override
    public long countFavoritesByUserId(UUID userId) {
        return jpaRepository.countByUserIdAndFavoriteTrueAndDeletedFalse(userId);
    }

    @Override
    public void delete(Memory memory) {
        MemoryDomainEntity entity = MemoryEntityMapper.toEntity(memory);
        jpaRepository.save(entity);
    }
}
