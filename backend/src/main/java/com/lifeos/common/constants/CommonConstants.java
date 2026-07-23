package com.lifeos.common.constants;

import java.util.UUID;

/**
 * Enterprise System-wide Common Constants.
 */
public final class CommonConstants {

    private CommonConstants() {
        // Private constructor for utility constant class
    }

    public static final String SYSTEM_USER_NAME = "SYSTEM";
    public static final UUID SYSTEM_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_SORT_DIRECTION = "DESC";
    public static final String DEFAULT_SORT_BY = "createdAt";

    public static final String UTC_TIMEZONE = "UTC";
    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
}
