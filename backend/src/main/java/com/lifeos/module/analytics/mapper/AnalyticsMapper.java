package com.lifeos.module.analytics.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.analytics.dto.DashboardRequest;
import com.lifeos.module.analytics.dto.DashboardResponse;
import com.lifeos.module.analytics.dto.LifeScoreResponse;
import com.lifeos.module.analytics.dto.ProductivityMetricResponse;
import com.lifeos.module.analytics.dto.UsageStatisticResponse;
import com.lifeos.module.analytics.entity.DashboardEntity;
import com.lifeos.module.analytics.entity.LifeScoreEntity;
import com.lifeos.module.analytics.entity.ProductivityMetricEntity;
import com.lifeos.module.analytics.entity.UsageStatisticEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for Analytics entities and DTOs.
 */
@Mapper(config = MapStructConfig.class)
public interface AnalyticsMapper {

    DashboardResponse toDto(DashboardEntity entity);

    DashboardEntity toEntity(DashboardRequest request);

    List<DashboardResponse> toDashboardDtoList(List<DashboardEntity> entityList);

    void updateEntityFromDto(DashboardRequest request, @MappingTarget DashboardEntity entity);

    ProductivityMetricResponse toDto(ProductivityMetricEntity entity);

    List<ProductivityMetricResponse> toProductivityDtoList(List<ProductivityMetricEntity> entityList);

    LifeScoreResponse toDto(LifeScoreEntity entity);

    List<LifeScoreResponse> toLifeScoreDtoList(List<LifeScoreEntity> entityList);

    UsageStatisticResponse toDto(UsageStatisticEntity entity);

    List<UsageStatisticResponse> toUsageDtoList(List<UsageStatisticEntity> entityList);
}
