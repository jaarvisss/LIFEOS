package com.lifeos.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled background job for preparing daily summaries and user notifications.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DailySummaryScheduler {

    // Runs every day at 08:00 AM
    @Scheduled(cron = "0 0 8 * * *")
    public void generateDailySummaries() {
        log.info("Executing scheduled job: generateDailySummaries");
    }
}
