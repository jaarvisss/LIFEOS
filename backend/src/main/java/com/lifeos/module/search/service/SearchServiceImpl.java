package com.lifeos.module.search.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.search.dto.SearchRequest;
import com.lifeos.module.search.dto.SearchResultResponse;
import com.lifeos.module.search.entity.SearchIndexEntity;
import com.lifeos.module.search.repository.SearchIndexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of SearchService interface.
 */
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchIndexRepository searchIndexRepository;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<SearchResultResponse> globalSearch(UUID userId, SearchRequest request, PaginationRequest paginationRequest) {
        Page<SearchIndexEntity> page = searchIndexRepository.findByUserIdAndTitleContainingIgnoreCaseOrContentSummaryContainingIgnoreCase(
                userId, request.query(), request.query(), paginationRequest.toPageable()
        );

        return PagedResponse.from(page.map(item -> new SearchResultResponse(
                item.getId(), item.getEntityType(), item.getEntityId(), item.getTitle(), item.getContentSummary(), item.getTags()
        )));
    }

    @Override
    @Transactional
    public void indexEntity(UUID userId, String entityType, UUID entityId, String title, String contentSummary, String tags) {
        SearchIndexEntity index = searchIndexRepository.findByUserIdAndEntityTypeAndEntityId(userId, entityType, entityId)
                .orElseGet(() -> SearchIndexEntity.builder()
                        .userId(userId)
                        .entityType(entityType)
                        .entityId(entityId)
                        .build());

        index.setTitle(title);
        index.setContentSummary(contentSummary);
        index.setTags(tags);
        searchIndexRepository.save(index);
    }

    @Override
    @Transactional
    public void deindexEntity(UUID userId, String entityType, UUID entityId) {
        searchIndexRepository.findByUserIdAndEntityTypeAndEntityId(userId, entityType, entityId)
                .ifPresent(searchIndexRepository::delete);
    }
}
