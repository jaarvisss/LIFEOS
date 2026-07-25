import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../design_system/primitives/glass_container.dart';

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        title: const Text('Profile & Settings'),
        backgroundColor: AppColors.surface,
        elevation: 0,
      ),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // User Profile Header Card
              GlassContainer(
                padding: const EdgeInsets.all(20),
                child: Row(
                  children: [
                    Container(
                      width: 60,
                      height: 60,
                      decoration: BoxDecoration(
                        color: AppColors.primaryLight,
                        shape: BoxShape.circle,
                        border: Border.all(color: AppColors.primary, width: 2),
                      ),
                      child: const Center(
                        child: Text(
                          'J',
                          style: TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                            color: AppColors.primary,
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(width: 16),
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text(
                            'Jagadhan',
                            style: TextStyle(
                              fontSize: 20,
                              fontWeight: FontWeight.bold,
                              color: AppColors.textPrimary,
                            ),
                          ),
                          const SizedBox(height: 2),
                          const Text(
                            'jagadhanelalit@gmail.com',
                            style: TextStyle(
                              fontSize: 13,
                              color: AppColors.textSecondary,
                            ),
                          ),
                          const SizedBox(height: 8),
                          Container(
                            padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                            decoration: BoxDecoration(
                              color: AppColors.primaryLight,
                              borderRadius: BorderRadius.circular(8),
                            ),
                            child: const Text(
                              'Professional Profile',
                              style: TextStyle(
                                fontSize: 12,
                                fontWeight: FontWeight.bold,
                                color: AppColors.primary,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 24),

              // Life Profile Switcher
              GlassContainer(
                padding: const EdgeInsets.all(16),
                child: Row(
                  children: [
                    Container(
                      padding: const EdgeInsets.all(10),
                      decoration: BoxDecoration(
                        color: AppColors.secondary.withValues(alpha: 0.12),
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: const Icon(Icons.swap_horiz_rounded, color: AppColors.secondary, size: 24),
                    ),
                    const SizedBox(width: 14),
                    const Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Switch Life Profile',
                            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 14, color: AppColors.textPrimary),
                          ),
                          SizedBox(height: 2),
                          Text(
                            'Change between Professional, Student, Entrepreneur',
                            style: TextStyle(fontSize: 12, color: AppColors.textSecondary),
                          ),
                        ],
                      ),
                    ),
                    IconButton(
                      icon: const Icon(Icons.chevron_right, color: AppColors.textSecondary),
                      onPressed: () => context.go('/onboarding'),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 28),

              // Settings Section Header
              const Text(
                'Settings & Preferences',
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: AppColors.textPrimary),
              ),
              const SizedBox(height: 12),

              // Settings List Items
              GlassContainer(
                padding: EdgeInsets.zero,
                child: Column(
                  children: [
                    ListTile(
                      leading: const Icon(Icons.shield_outlined, color: AppColors.primary),
                      title: const Text('Security & PII Protection', style: TextStyle(fontWeight: FontWeight.w600)),
                      subtitle: const Text('Biometric auth, zero-knowledge keys'),
                      trailing: const Icon(Icons.chevron_right, color: AppColors.textSecondary),
                      onTap: () {},
                    ),
                    const Divider(height: 1, color: AppColors.border),
                    ListTile(
                      leading: const Icon(Icons.psychology_outlined, color: AppColors.primary),
                      title: const Text('AI Memory Graph', style: TextStyle(fontWeight: FontWeight.w600)),
                      subtitle: const Text('Manage vector indices & memory wipes'),
                      trailing: const Icon(Icons.chevron_right, color: AppColors.textSecondary),
                      onTap: () {},
                    ),
                    const Divider(height: 1, color: AppColors.border),
                    ListTile(
                      leading: const Icon(Icons.palette_outlined, color: AppColors.primary),
                      title: const Text('Appearance & Theme', style: TextStyle(fontWeight: FontWeight.w600)),
                      subtitle: const Text('Pure Light Glassmorphic Theme'),
                      trailing: const Icon(Icons.chevron_right, color: AppColors.textSecondary),
                      onTap: () {},
                    ),
                    const Divider(height: 1, color: AppColors.border),
                    ListTile(
                      leading: const Icon(Icons.download_outlined, color: AppColors.primary),
                      title: const Text('Export My Data', style: TextStyle(fontWeight: FontWeight.w600)),
                      subtitle: const Text('JSON & Markdown offline export'),
                      trailing: const Icon(Icons.chevron_right, color: AppColors.textSecondary),
                      onTap: () {},
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 24),

              // Logout Button
              SizedBox(
                width: double.infinity,
                height: 50,
                child: OutlinedButton.icon(
                  onPressed: () => context.go('/onboarding'),
                  icon: const Icon(Icons.logout_rounded, color: AppColors.error),
                  label: const Text(
                    'Sign Out',
                    style: TextStyle(fontWeight: FontWeight.bold, color: AppColors.error),
                  ),
                  style: OutlinedButton.styleFrom(
                    side: const BorderSide(color: AppColors.error),
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
                  ),
                ),
              ),
              const SizedBox(height: 20),
            ],
          ),
        ),
      ),
    );
  }
}
