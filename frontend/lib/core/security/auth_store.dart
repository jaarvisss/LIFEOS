import 'package:flutter_riverpod/flutter_riverpod.dart';

class AuthState {
  final bool isAuthenticated;
  final String? userId;
  final String? accessToken;

  const AuthState({
    this.isAuthenticated = false,
    this.userId,
    this.accessToken,
  });

  AuthState copyWith({
    bool? isAuthenticated,
    String? userId,
    String? accessToken,
  }) {
    return AuthState(
      isAuthenticated: isAuthenticated ?? this.isAuthenticated,
      userId: userId ?? this.userId,
      accessToken: accessToken ?? this.accessToken,
    );
  }
}

class AuthNotifier extends StateNotifier<AuthState> {
  AuthNotifier() : super(const AuthState());

  void setAuthenticated(String userId, String token) {
    state = AuthState(isAuthenticated: true, userId: userId, accessToken: token);
  }

  void logout() {
    state = const AuthState();
  }
}

final authProvider = StateNotifierProvider<AuthNotifier, AuthState>((ref) {
  return AuthNotifier();
});
