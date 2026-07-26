package com.lifeos.domain.ai;

import com.lifeos.domain.ai.dashboard.dto.*;
import com.lifeos.domain.ai.dashboard.service.DashboardIntelligenceServiceImpl;
import com.lifeos.domain.memory.reasoning.dto.DailyBriefResponseDTO;
import com.lifeos.domain.memory.reasoning.service.DailyBriefService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Unit Test Suite for AI Dashboard Intelligence Platform & Life Operating Cockpit.
 */
class AiDashboardServiceTest {

    @Test
    @DisplayName("Should calculate 0-100 Life Operating Scores with explanation")
    void shouldCalculateLifeScore() {
        DailyBriefService mockBriefService = Mockito.mock(DailyBriefService.class);
        DashboardIntelligenceServiceImpl service = new DashboardIntelligenceServiceImpl(mockBriefService);
        UUID userId = UUID.randomUUID();

        LifeScoreDTO score = service.calculateLifeScore(userId);

        assertNotNull(score);
        assertEquals(88, score.getOverallLifeScore());
        assertEquals(90, score.getProductivityScore());
        assertEquals(92, score.getHealthScore());
        assertEquals(85, score.getFinanceScore());
        assertNotNull(score.getScoreSummaryExplanation());
    }

    @Test
    @DisplayName("Should generate proactive cross-domain insights and predictions")
    void shouldGenerateInsightsAndPredictions() {
        DailyBriefService mockBriefService = Mockito.mock(DailyBriefService.class);
        DashboardIntelligenceServiceImpl service = new DashboardIntelligenceServiceImpl(mockBriefService);
        UUID userId = UUID.randomUUID();

        List<DashboardInsightDTO> insights = service.getProactiveInsights(userId);
        assertNotNull(insights);
        assertTrue(insights.size() >= 4);

        List<DashboardPredictionDTO> predictions = service.getPredictions(userId);
        assertNotNull(predictions);
        assertTrue(predictions.stream().anyMatch(p -> p.getProbabilityPercentage() == 87));
    }

    @Test
    @DisplayName("Should synthesize complete dashboard overview with Daily Briefing and Life Scores")
    void shouldGetDashboardOverview() {
        DailyBriefService mockBriefService = Mockito.mock(DailyBriefService.class);
        UUID userId = UUID.randomUUID();

        Mockito.when(mockBriefService.generateDailyBrief(any())).thenReturn(
                DailyBriefResponseDTO.builder()
                        .formattedBrief("Good Morning! Priorities: Phase 9 Certification.")
                        .build()
        );

        DashboardIntelligenceServiceImpl service = new DashboardIntelligenceServiceImpl(mockBriefService);

        DashboardOverviewDTO overview = service.getDashboardOverview(userId);

        assertNotNull(overview);
        assertNotNull(overview.getLifeScore());
        assertTrue(overview.getDailyBriefingSummary().contains("Good Morning!"));
        assertFalse(overview.getProactiveInsights().isEmpty());
    }
}
