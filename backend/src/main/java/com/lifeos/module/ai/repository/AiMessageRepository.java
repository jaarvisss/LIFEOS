package com.lifeos.module.ai.repository;

import com.lifeos.module.ai.entity.AiMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for ai.ai_messages table.
 */
@Repository
public interface AiMessageRepository extends JpaRepository<AiMessageEntity, UUID> {

    List<AiMessageEntity> findByConversationIdOrderByCreatedAtAsc(UUID conversationId);
}
