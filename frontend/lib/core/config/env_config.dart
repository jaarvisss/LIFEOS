class EnvConfig {
  static const String appName = 'LifeOS';
  static const String appVersion = '1.0.0';
  static const String apiBaseUrl = String.fromEnvironment(
    'API_BASE_URL',
    defaultValue: 'http://localhost:8080/api/v1',
  );
  static const String wsBaseUrl = String.fromEnvironment(
    'WS_BASE_URL',
    defaultValue: 'ws://localhost:8080/ws',
  );

  static Future<void> init() async {
    // Environment initialization logic
  }
}
