import 'package:flutter/material.dart';

class RouteGuards {
  static bool canAccessDomain(String domain, bool isAuthenticated) {
    return isAuthenticated;
  }
}

class NavigationService {
  static final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();
}
