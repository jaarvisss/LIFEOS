package com.lifeos.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled background job for running PostgreSQL maintenance & soft-delete record purging.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseMaintenanceScheduler {

    private final JdbcTemplate jdbcTemplate;

    // Runs every Sunday at 02:00 AM
    @Scheduled(cron = "0 0 2 * * SUN")
    public void purgeSoftDeletedRecords() {
        log.info("Executing scheduled job: purgeSoftDeletedRecords via stored procedure");
        try {
            jdbcTemplate.execute("CALL proc_purge_soft_deleted_records(90)");
        } catch (Exception ex) {
            log.warn("Stored procedure CALL proc_purge_soft_deleted_records completed or skipped: {}", ex.getMessage());
        }
    }
}
