package com.lifeos.module.analytics.service;

import com.lifeos.module.analytics.dto.DashboardRequest;
import com.lifeos.module.analytics.dto.DashboardResponse;
import com.lifeos.module.analytics.dto.LifeScoreResponse;
import com.lifeos.module.analytics.dto.ProductivityMetricResponse;
import com.lifeos.module.analytics.dto.UsageStatisticResponse;
import com.lifeos.module.analytics.entity.DashboardEntity;
import com.lifeos.module.analytics.entity.LifeScoreEntity;
import com.lifeos.module.analytics.entity.ProductivityMetricEntity;
import com.lifeos.module.analytics.entity.UsageStatisticEntity;
import com.lifeos.module.analytics.mapper.AnalyticsMapper;
import com.lifeos.module.analytics.repository.DashboardRepository;
import com.lifeos.module.analytics.repository.LifeScoreRepository;
import com.lifeos.module.analytics.repository.ProductivityMetricRepository;
import com.lifeos.module.analytics.repository.UsageStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of AnalyticsService interface.
 */
@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final DashboardRepository dashboardRepository;
    private final ProductivityMetricRepository productivityMetricRepository;
    private final LifeScoreRepository lifeScoreRepository;
    private final UsageStatisticRepository usageStatisticRepository;
    private final AnalyticsMapper analyticsMapper;

    @Override
    @Transactional
    public DashboardResponse createDashboard(UUID userId, DashboardRequest request) {
        DashboardEntity dashboard = analyticsMapper.toEntity(request);
        dashboard.setUserId(userId);
        dashboard = dashboardRepository.save(dashboard);
        return analyticsMapper.toDto(dashboard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DashboardResponse> getDashboards(UUID userId) {
        return analyticsMapper.toDashboardDtoList(dashboardRepository.findByUserId(userId));
    }

    @Override
    @Transactional
    public ProductivityMetricResponse getTodayProductivity(UUID userId) {
        LocalDate today = LocalDate.now();
        ProductivityMetricEntity metric = productivityMetricRepository.findByUserIdAndMetricDate(userId, today)
                .orElseGet(() -> ProductivityMetricEntity.builder()
                        .userId(userId)
                        .metricDate(today)
                        .tasksCompletedCount(0)
                        .habitsCompletedCount(0)
                        .focusMinutesTotal(0)
                        .productivityScore(new BigDecimal("75.00"))
                        .build());

        if (metric.getId() == null) {
            metric = productivityMetricRepository.save(metric);
        }
        return analyticsMapper.toDto(metric);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductivityMetricResponse> getProductivityRange(UUID userId, LocalDate start, LocalDate end) {
        return analyticsMapper.toProductivityDtoList(
                productivityMetricRepository.findByUserIdAndMetricDateBetweenOrderByMetricDateAsc(userId, start, end)
        );
    }

    @Override
    @Transactional
    public LifeScoreResponse getLatestLifeScore(UUID userId) {
        LocalDate today = LocalDate.now();
        LifeScoreEntity score = lifeScoreRepository.findByUserIdAndScoreDate(userId, today)
                .orElseGet(() -> LifeScoreEntity.builder()
                        .userId(userId)
                        .scoreDate(today)
                        .overallScore(new BigDecimal("82.50"))
                        .healthSubscore(new BigDecimal("85.00"))
                        .financeSubscore(new BigDecimal("80.00"))
                        .productivitySubscore(new BigDecimal("88.00"))
                        .relationshipSubscore(new BigDecimal("77.00"))
                        .build());

        if (score.getId() == null) {
            score = lifeScoreRepository.save(score);
        }
        return analyticsMapper.toDto(score);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsageStatisticResponse> getUsageStatistics(UUID userId) {
        return analyticsMapper.toUsageDtoList(usageStatisticRepository.findByUserIdOrderByLastUsedAtDesc(userId));
    }

    @Override
    @Transactional
    public void recordFeatureUsage(UUID userId, String featureName) {
        UsageStatisticEntity stat = usageStatisticRepository.findByUserIdAndFeatureName(userId, featureName)
                .orElseGet(() -> UsageStatisticEntity.builder()
                        .userId(userId)
                        .featureName(featureName)
                        .actionCount(0)
                        .build());

        stat.setActionCount(stat.getActionCount() + 1);
        stat.setLastUsedAt(Instant.now());
        usageStatisticRepository.save(stat);
    }
}
