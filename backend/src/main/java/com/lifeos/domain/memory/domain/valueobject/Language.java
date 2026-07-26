package com.lifeos.domain.memory.domain.valueobject;

import com.lifeos.domain.memory.domain.exception.MemoryValidationException;

/**
 * Value Object for Language Code.
 */
public record Language(String code) {
    public Language {
        if (code == null || code.trim().isEmpty()) {
            code = "en";
        } else {
            code = code.trim().toLowerCase();
            if (code.length() > 10) {
                throw new MemoryValidationException("Language code cannot exceed 10 characters");
            }
        }
    }

    public static Language of(String code) {
        return new Language(code);
    }

    public static Language defaultLanguage() {
        return new Language("en");
    }
}
