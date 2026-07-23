package com.lifeos.common.util;

import com.lifeos.common.exception.ValidationException;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Enterprise UUID Utilities for safe parsing and validation.
 */
public final class UUIDUtils {

    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
    );

    private UUIDUtils() {
    }

    public static UUID generate() {
        return UUID.randomUUID();
    }

    public static boolean isValid(String uuidString) {
        if (uuidString == null || uuidString.isBlank()) {
            return false;
        }
        return UUID_PATTERN.matcher(uuidString).matches();
    }

    public static UUID parse(String uuidString) {
        if (!isValid(uuidString)) {
            throw new ValidationException("Invalid UUID format: " + uuidString);
        }
        return UUID.fromString(uuidString);
    }

    public static UUID parseOrNull(String uuidString) {
        if (!isValid(uuidString)) {
            return null;
        }
        return UUID.fromString(uuidString);
    }
}
