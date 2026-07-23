package com.lifeos.event;

import com.lifeos.module.analytics.service.AnalyticsService;
import com.lifeos.module.audit.service.AuditService;
import com.lifeos.module.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Event listener handling domain events asynchronously for metrics aggregation, search indexing, and audit logs.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DomainEventListener {

    private final AnalyticsService analyticsService;
    private final AuditService auditService;
    private final SearchService searchService;

    @Async
    @EventListener
    public void handleTaskCompleted(TaskCompletedEvent event) {
        log.info("Handling TaskCompletedEvent for user: {} | task: {}", event.userId(), event.taskId());
        analyticsService.recordFeatureUsage(event.userId(), "TASK_COMPLETE");
        auditService.recordActivityLog(event.userId(), "TASK_COMPLETED", "Task completed: " + event.taskTitle(), null);
    }

    @Async
    @EventListener
    public void handleGoalAchieved(GoalAchievedEvent event) {
        log.info("Handling GoalAchievedEvent for user: {} | goal: {}", event.userId(), event.goalId());
        analyticsService.recordFeatureUsage(event.userId(), "GOAL_ACHIEVED");
        auditService.recordActivityLog(event.userId(), "GOAL_ACHIEVED", "Goal achieved: " + event.goalTitle(), null);
        searchService.indexEntity(event.userId(), "GOAL", event.goalId(), event.goalTitle(), "Achieved goal", "ACHIEVED");
    }
}
