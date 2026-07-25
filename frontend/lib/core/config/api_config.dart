import 'env_config.dart';

class ApiConfig {
  static String get baseUrl => EnvConfig.apiBaseUrl;
  static const Duration timeout = Duration(seconds: 30);
  
  // Endpoints
  static const String authLogin = '/auth/login';
  static const String authRefresh = '/auth/refresh';
  static const String tasks = '/tasks';
  static const String goals = '/goals';
  static const String finance = '/finance';
  static const String health = '/health';
  static const String aiCapture = '/ai/capture';
  static const String aiContext = '/ai/context';
}
