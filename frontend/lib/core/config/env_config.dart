import 'dart:io' show Platform;
import 'package:flutter/foundation.dart' show kIsWeb;

class EnvConfig {
  static const String appName = 'LifeOS';
  static const String appVersion = '1.0.0';

  static String get apiBaseUrl {
    const customUrl = String.fromEnvironment('API_BASE_URL', defaultValue: '');
    if (customUrl.isNotEmpty) return customUrl;

    if (kIsWeb) {
      return 'http://localhost:8080/api/v1';
    } else if (Platform.isAndroid) {
      // Android Emulator maps 10.0.2.2 to the host machine's localhost
      return 'http://10.0.2.2:8080/api/v1';
    } else {
      return 'http://localhost:8080/api/v1';
    }
  }

  static String get wsBaseUrl {
    const customWs = String.fromEnvironment('WS_BASE_URL', defaultValue: '');
    if (customWs.isNotEmpty) return customWs;

    if (kIsWeb) {
      return 'ws://localhost:8080/ws';
    } else if (Platform.isAndroid) {
      return 'ws://10.0.2.2:8080/ws';
    } else {
      return 'ws://localhost:8080/ws';
    }
  }

  static Future<void> init() async {
    // Environment initialization logic
  }
}
