package com.lifeos.module.ai.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.ai.dto.AiConversationRequest;
import com.lifeos.module.ai.dto.AiConversationResponse;
import com.lifeos.module.ai.dto.AiMemoryRequest;
import com.lifeos.module.ai.dto.AiMemoryResponse;
import com.lifeos.module.ai.dto.AiMessageResponse;
import com.lifeos.module.ai.dto.AiSuggestionResponse;
import com.lifeos.module.ai.entity.AiConversationEntity;
import com.lifeos.module.ai.entity.AiMemoryEntity;
import com.lifeos.module.ai.entity.AiMessageEntity;
import com.lifeos.module.ai.entity.AiSuggestionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for AI Platform entities and DTOs.
 */
@Mapper(config = MapStructConfig.class)
public interface AiMapper {

    AiConversationResponse toDto(AiConversationEntity entity);

    AiConversationEntity toEntity(AiConversationRequest request);

    List<AiConversationResponse> toConversationDtoList(List<AiConversationEntity> entityList);

    void updateEntityFromDto(AiConversationRequest request, @MappingTarget AiConversationEntity entity);

    AiMessageResponse toDto(AiMessageEntity entity);

    List<AiMessageResponse> toMessageDtoList(List<AiMessageEntity> entityList);

    AiMemoryResponse toDto(AiMemoryEntity entity);

    AiMemoryEntity toEntity(AiMemoryRequest request);

    List<AiMemoryResponse> toMemoryDtoList(List<AiMemoryEntity> entityList);

    void updateEntityFromDto(AiMemoryRequest request, @MappingTarget AiMemoryEntity entity);

    AiSuggestionResponse toDto(AiSuggestionEntity entity);

    List<AiSuggestionResponse> toSuggestionDtoList(List<AiSuggestionEntity> entityList);
}
