package com.lifeos.domain.memory.reasoning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Executive Daily AI Briefing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyBriefResponseDTO {
    private String greeting;
    private String todayFocusSummary;
    private List<String> scheduleItems;
    private List<String> priorityTasks;
    private List<ProactiveInsightDTO> proactiveAlerts;
    private String motivationalQuote;
    private String healthReminder;
    private String budgetStatus;
}
