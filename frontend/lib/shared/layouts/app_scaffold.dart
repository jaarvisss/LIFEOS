import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../experiences/capture/universal_capture_modal.dart';

class AppScaffold extends StatelessWidget {
  final Widget child;
  const AppScaffold({super.key, required this.child});

  int _calculateSelectedIndex(BuildContext context) {
    final String location = GoRouterState.of(context).uri.toString();
    if (location.startsWith('/companion')) return 1;
    if (location.startsWith('/domains')) return 2;
    if (location.startsWith('/profile')) return 3;
    return 0;
  }

  void _onItemTapped(int index, BuildContext context) {
    switch (index) {
      case 0:
        context.go('/');
        break;
      case 1:
        context.go('/companion');
        break;
      case 2:
        context.go('/domains');
        break;
      case 3:
        context.go('/profile');
        break;
    }
  }

  @override
  Widget build(BuildContext context) {
    final selectedIndex = _calculateSelectedIndex(context);

    return Scaffold(
      backgroundColor: AppColors.background,
      body: child,
      floatingActionButton: Container(
        height: 56,
        width: 56,
        margin: const EdgeInsets.only(top: 20),
        decoration: BoxDecoration(
          gradient: const LinearGradient(
            colors: [Color(0xFF4F46E5), Color(0xFF06B6D4)],
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
          ),
          shape: BoxShape.circle,
          boxShadow: [
            BoxShadow(
              color: const Color(0xFF4F46E5).withValues(alpha: 0.4),
              blurRadius: 16,
              offset: const Offset(0, 6),
            ),
          ],
        ),
        child: FloatingActionButton(
          elevation: 0,
          highlightElevation: 0,
          backgroundColor: Colors.transparent,
          onPressed: () {
            showModalBottomSheet(
              context: context,
              isScrollControlled: true,
              backgroundColor: Colors.transparent,
              builder: (context) => const UniversalCaptureModal(),
            );
          },
          child: const Icon(Icons.add_rounded, color: Colors.white, size: 30),
        ),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      bottomNavigationBar: BottomAppBar(
        color: AppColors.surface,
        elevation: 8,
        shape: const CircularNotchedRectangle(),
        notchMargin: 8.0,
        height: 66,
        padding: const EdgeInsets.symmetric(horizontal: 6),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            _buildNavItem(
              context: context,
              icon: Icons.home_rounded,
              label: 'Home',
              isSelected: selectedIndex == 0,
              onTap: () => _onItemTapped(0, context),
            ),
            _buildNavItem(
              context: context,
              icon: Icons.auto_awesome_rounded,
              label: 'Companion',
              isSelected: selectedIndex == 1,
              onTap: () => _onItemTapped(1, context),
            ),
            const SizedBox(width: 48), // Notch gap for FAB
            _buildNavItem(
              context: context,
              icon: Icons.grid_view_rounded,
              label: 'Domains',
              isSelected: selectedIndex == 2,
              onTap: () => _onItemTapped(2, context),
            ),
            _buildNavItem(
              context: context,
              icon: Icons.person_rounded,
              label: 'Profile',
              isSelected: selectedIndex == 3,
              onTap: () => _onItemTapped(3, context),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildNavItem({
    required BuildContext context,
    required IconData icon,
    required String label,
    required bool isSelected,
    required VoidCallback onTap,
  }) {
    final color = isSelected ? AppColors.primary : AppColors.textSecondary;
    return InkWell(
      onTap: onTap,
      borderRadius: BorderRadius.circular(12),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 2.0),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, color: color, size: 22),
            const SizedBox(height: 3),
            Text(
              label,
              style: TextStyle(
                fontSize: 11,
                fontWeight: isSelected ? FontWeight.bold : FontWeight.w500,
                color: color,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
