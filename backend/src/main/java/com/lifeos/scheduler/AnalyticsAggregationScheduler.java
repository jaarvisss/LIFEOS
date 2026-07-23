package com.lifeos.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled background job for calculating daily productivity metrics and life scores.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticsAggregationScheduler {

    // Runs every hour
    @Scheduled(cron = "0 0 * * * *")
    public void aggregateHourlyAnalytics() {
        log.info("Executing scheduled job: aggregateHourlyAnalytics");
    }
}
