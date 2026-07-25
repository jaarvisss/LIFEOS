import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../design_system/primitives/glass_container.dart';

class OnboardingFlow extends StatefulWidget {
  const OnboardingFlow({super.key});

  @override
  State<OnboardingFlow> createState() => _OnboardingFlowState();
}

class _OnboardingFlowState extends State<OnboardingFlow> {
  String _selectedProfile = 'Professional';

  final List<Map<String, dynamic>> _profiles = [
    {'title': 'Student', 'icon': Icons.school_outlined, 'desc': 'Deadlines, exam schedules & habit streak tracking.'},
    {'title': 'Professional', 'icon': Icons.work_outline, 'desc': 'Project tracking, high-velocity calendar & meeting notes.'},
    {'title': 'Entrepreneur', 'icon': Icons.rocket_launch_outlined, 'desc': 'Strategic OKRs, multi-domain focus & cash flow.'},
    {'title': 'Freelancer', 'icon': Icons.laptop_mac_outlined, 'desc': 'Client CRM, flexible time-blocks & invoice tracking.'},
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const SizedBox(height: 20),
              Row(
                children: [
                  Container(
                    padding: const EdgeInsets.all(10),
                    decoration: BoxDecoration(
                      color: AppColors.primaryLight,
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: const Icon(Icons.auto_awesome, color: AppColors.primary, size: 24),
                  ),
                  const SizedBox(width: 12),
                  const Text(
                    'Welcome to LifeOS',
                    style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold, color: AppColors.textPrimary),
                  ),
                ],
              ),
              const SizedBox(height: 16),
              const Text(
                'Select your Life Profile to personalize your AI operating context:',
                style: TextStyle(fontSize: 14, color: AppColors.textSecondary, height: 1.4),
              ),
              const SizedBox(height: 24),
              Expanded(
                child: ListView.separated(
                  itemCount: _profiles.length,
                  separatorBuilder: (context, index) => const SizedBox(height: 12),
                  itemBuilder: (context, index) {
                    final item = _profiles[index];
                    final isSelected = _selectedProfile == item['title'];
                    return GestureDetector(
                      onTap: () => setState(() => _selectedProfile = item['title']),
                      child: GlassContainer(
                        color: isSelected ? AppColors.primaryLight : AppColors.surface,
                        borderColor: isSelected ? AppColors.primary : AppColors.border,
                        child: Row(
                          children: [
                            Icon(
                              item['icon'] as IconData,
                              color: isSelected ? AppColors.primary : AppColors.textSecondary,
                              size: 28,
                            ),
                            const SizedBox(width: 16),
                            Expanded(
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    item['title'] as String,
                                    style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 16,
                                      color: isSelected ? AppColors.primary : AppColors.textPrimary,
                                    ),
                                  ),
                                  const SizedBox(height: 4),
                                  Text(
                                    item['desc'] as String,
                                    style: const TextStyle(fontSize: 12, color: AppColors.textSecondary),
                                  ),
                                ],
                              ),
                            ),
                            if (isSelected)
                              const Icon(Icons.check_circle, color: AppColors.primary, size: 22),
                          ],
                        ),
                      ),
                    );
                  },
                ),
              ),
              SizedBox(
                width: double.infinity,
                height: 52,
                child: ElevatedButton(
                  onPressed: () => context.go('/login'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColors.primary,
                    elevation: 0,
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
                  ),
                  child: const Text(
                    'Continue to Sign In',
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: Colors.white),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
