import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../experiences/onboarding/splash_screen.dart';
import '../../experiences/onboarding/onboarding_flow.dart';
import '../../experiences/auth/login_screen.dart';
import '../../experiences/auth/signup_screen.dart';
import '../../experiences/home/home_screen.dart';
import '../../experiences/companion/companion_screen.dart';
import '../../experiences/domains/domain_hub_screen.dart';
import '../../experiences/profile/profile_screen.dart';
import '../../shared/layouts/app_scaffold.dart';

final appRouterProvider = Provider<GoRouter>((ref) {
  return GoRouter(
    initialLocation: '/splash',
    routes: [
      GoRoute(
        path: '/splash',
        builder: (context, state) => const SplashScreen(),
      ),
      GoRoute(
        path: '/onboarding',
        builder: (context, state) => const OnboardingFlow(),
      ),
      GoRoute(
        path: '/login',
        builder: (context, state) => const LoginScreen(),
      ),
      GoRoute(
        path: '/signup',
        builder: (context, state) => const SignupScreen(),
      ),
      ShellRoute(
        builder: (context, state, child) => AppScaffold(child: child),
        routes: [
          GoRoute(
            path: '/',
            builder: (context, state) => const HomeScreen(),
          ),
          GoRoute(
            path: '/companion',
            builder: (context, state) => const CompanionScreen(),
          ),
          GoRoute(
            path: '/domains',
            builder: (context, state) => const DomainHubScreen(),
          ),
          GoRoute(
            path: '/profile',
            builder: (context, state) => const ProfileScreen(),
          ),
        ],
      ),
    ],
  );
});
