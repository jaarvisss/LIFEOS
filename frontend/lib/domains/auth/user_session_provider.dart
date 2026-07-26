import 'package:flutter_riverpod/flutter_riverpod.dart';

class UserSession {
  final String fullName;
  final String email;
  final String token;

  UserSession({
    required this.fullName,
    required this.email,
    required this.token,
  });

  String get firstName {
    if (fullName.trim().isEmpty) return 'User';
    final parts = fullName.trim().split(' ');
    return parts.first;
  }
}

class UserSessionNotifier extends StateNotifier<UserSession> {
  UserSessionNotifier()
      : super(UserSession(
          fullName: 'Rohan Deshmukh',
          email: 'rohan.deshmukh@example.com',
          token: 'active_session_token',
        ));

  void updateUserSession({required String fullName, required String email, String? token}) {
    state = UserSession(
      fullName: fullName.isNotEmpty ? fullName : 'User',
      email: email.isNotEmpty ? email : 'user@example.com',
      token: token ?? state.token,
    );
  }
}

final userSessionProvider = StateNotifierProvider<UserSessionNotifier, UserSession>((ref) {
  return UserSessionNotifier();
});
