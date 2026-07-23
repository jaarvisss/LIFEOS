package com.lifeos.module.ai.repository;

import com.lifeos.module.ai.entity.AiConversationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for ai.ai_conversations table.
 */
@Repository
public interface AiConversationRepository extends JpaRepository<AiConversationEntity, UUID> {

    Page<AiConversationEntity> findByUserIdAndIsDeletedFalseOrderByUpdatedAtDesc(UUID userId, Pageable pageable);

    Optional<AiConversationEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);
}
