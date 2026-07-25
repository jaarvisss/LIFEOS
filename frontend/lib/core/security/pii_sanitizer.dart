class PiiSanitizer {
  static final RegExp _ssnPattern = RegExp(r'\b\d{3}-\d{2}-\d{4}\b');
  static final RegExp _creditCardPattern = RegExp(r'\b(?:\d[ -]*?){13,16}\b');

  static String sanitize(String input) {
    String sanitized = input.replaceAll(_ssnPattern, '[REDACTED_SSN]');
    sanitized = sanitized.replaceAll(_creditCardPattern, '[REDACTED_CARD]');
    return sanitized;
  }
}
