import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import '../../core/config/env_config.dart';

class AuthRepository {
  final Dio _dio = Dio(BaseOptions(
    connectTimeout: const Duration(seconds: 10),
    receiveTimeout: const Duration(seconds: 10),
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
  ));
  final String _baseUrl = EnvConfig.apiBaseUrl;

  Future<Map<String, dynamic>> login(String email, String password) async {
    try {
      final response = await _dio.post(
        '$_baseUrl/auth/login',
        data: {
          'email': email,
          'password': password,
        },
      );

      if (response.statusCode == 200) {
        final data = response.data;
        if (data is Map<String, dynamic>) {
          return {'success': true, 'data': data['data'], 'message': 'Login successful'};
        }
      }
      return {'success': true, 'message': 'Login successful'};
    } on DioException catch (e) {
      debugPrint('LOGIN API ERROR: ${e.response?.data ?? e.message}');
      final msg = e.response?.data?['message'] ?? e.message ?? 'Login failed';
      return {'success': false, 'message': msg};
    } catch (e) {
      debugPrint('LOGIN UNKNOWN ERROR: $e');
      return {'success': false, 'message': e.toString()};
    }
  }

  Future<Map<String, dynamic>> register(String fullName, String email, String password) async {
    try {
      final nameParts = fullName.trim().split(' ');
      final firstName = nameParts.isNotEmpty ? nameParts.first : fullName;
      final lastName = nameParts.length > 1 ? nameParts.sublist(1).join(' ') : 'User';

      final response = await _dio.post(
        '$_baseUrl/auth/register',
        data: {
          'email': email,
          'password': password,
          'firstName': firstName,
          'lastName': lastName,
        },
      );

      if (response.statusCode == 201 || response.statusCode == 200) {
        final data = response.data;
        if (data is Map<String, dynamic>) {
          return {'success': true, 'data': data['data'], 'message': 'User registered successfully in database!'};
        }
        return {'success': true, 'message': 'User registered successfully!'};
      } else {
        return {'success': false, 'message': 'Registration returned status ${response.statusCode}'};
      }
    } on DioException catch (e) {
      debugPrint('REGISTER API ERROR: ${e.response?.data ?? e.message}');
      final msg = e.response?.data?['message'] ?? e.message ?? 'Registration API failed';
      return {'success': false, 'message': msg};
    } catch (e) {
      debugPrint('REGISTER UNKNOWN ERROR: $e');
      return {'success': false, 'message': e.toString()};
    }
  }
}
