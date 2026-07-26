import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../design_system/primitives/glass_container.dart';
import '../../domains/auth/user_session_provider.dart';
import '../notification/notifications_modal.dart';

class HomeScreen extends ConsumerStatefulWidget {
  const HomeScreen({super.key});

  @override
  ConsumerState<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends ConsumerState<HomeScreen> {
  void _openNotifications() {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (context) => const NotificationsModal(),
    );
  }

  @override
  Widget build(BuildContext context) {
    final userSession = ref.watch(userSessionProvider);
    final String initialLetter = userSession.firstName.isNotEmpty ? userSession.firstName[0].toUpperCase() : 'U';

    return Scaffold(
      backgroundColor: AppColors.background,
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 20.0, vertical: 16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // 1. Top Bar: LifeOS+ Logo | Bell Icon | User Avatar
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  RichText(
                    text: const TextSpan(
                      children: [
                        TextSpan(
                          text: 'LifeOS',
                          style: TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.w800,
                            color: AppColors.textPrimary,
                            letterSpacing: -0.5,
                          ),
                        ),
                        TextSpan(
                          text: '+',
                          style: TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                            color: Color(0xFF6366F1),
                          ),
                        ),
                      ],
                    ),
                  ),
                  Row(
                    children: [
                      Stack(
                        children: [
                          Container(
                            decoration: const BoxDecoration(
                              color: Colors.white,
                              shape: BoxShape.circle,
                            ),
                            child: IconButton(
                              icon: const Icon(Icons.notifications_none_rounded, color: AppColors.textPrimary, size: 22),
                              onPressed: _openNotifications,
                            ),
                          ),
                          Positioned(
                            right: 12,
                            top: 12,
                            child: Container(
                              width: 8,
                              height: 8,
                              decoration: const BoxDecoration(
                                color: Color(0xFF6366F1),
                                shape: BoxShape.circle,
                              ),
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(width: 10),
                      GestureDetector(
                        onTap: () => context.go('/profile'),
                        child: Stack(
                          children: [
                            Container(
                              width: 40,
                              height: 40,
                              decoration: BoxDecoration(
                                color: const Color(0xFFEEF2FF),
                                shape: BoxShape.circle,
                                border: Border.all(color: const Color(0xFF6366F1), width: 1.5),
                              ),
                              child: Center(
                                child: Text(
                                  initialLetter,
                                  style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: Color(0xFF6366F1)),
                                ),
                              ),
                            ),
                            Positioned(
                              right: 0,
                              bottom: 0,
                              child: Container(
                                width: 10,
                                height: 10,
                                decoration: BoxDecoration(
                                  color: const Color(0xFF10B981),
                                  shape: BoxShape.circle,
                                  border: Border.all(color: Colors.white, width: 1.5),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ],
              ),
              const SizedBox(height: 24),

              // 2. Hero Greeting Section with Glowing Orb
              Stack(
                clipBehavior: Clip.none,
                children: [
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Good Morning,',
                        style: TextStyle(fontSize: 16, color: AppColors.textSecondary, fontWeight: FontWeight.w500),
                      ),
                      const SizedBox(height: 2),
                      Row(
                        children: [
                          Text(
                            userSession.firstName,
                            style: const TextStyle(
                              fontSize: 34,
                              fontWeight: FontWeight.bold,
                              fontFamily: 'Serif',
                              color: AppColors.textPrimary,
                              letterSpacing: -0.5,
                            ),
                          ),
                          const SizedBox(width: 8),
                          const Text('👋', style: TextStyle(fontSize: 28)),
                        ],
                      ),
                      const SizedBox(height: 8),
                      const Text(
                        'Focus on today,\nLifeOS handles the rest.',
                        style: TextStyle(fontSize: 14, color: AppColors.textSecondary, height: 1.4),
                      ),
                      const SizedBox(height: 20),
                      ElevatedButton(
                        onPressed: () => context.go('/companion'),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: const Color(0xFF0F172A),
                          padding: const EdgeInsets.symmetric(horizontal: 22, vertical: 12),
                          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(30)),
                          elevation: 0,
                        ),
                        child: const Row(
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            Icon(Icons.auto_awesome, color: Color(0xFF6366F1), size: 16),
                            SizedBox(width: 8),
                            Text('Ask LifeOS', style: TextStyle(color: Colors.white, fontWeight: FontWeight.w600, fontSize: 13)),
                          ],
                        ),
                      ),
                    ],
                  ),

                  // 3D Glowing Orb Graphic
                  Positioned(
                    right: 0,
                    top: -10,
                    child: SizedBox(
                      width: 140,
                      height: 140,
                      child: Stack(
                        alignment: Alignment.center,
                        children: [
                          Container(
                            width: 120,
                            height: 120,
                            decoration: BoxDecoration(
                              shape: BoxShape.circle,
                              boxShadow: [
                                BoxShadow(
                                  color: const Color(0xFF6366F1).withValues(alpha: 0.35),
                                  blurRadius: 40,
                                  spreadRadius: 10,
                                ),
                                BoxShadow(
                                  color: const Color(0xFFEC4899).withValues(alpha: 0.25),
                                  blurRadius: 30,
                                  spreadRadius: 5,
                                ),
                              ],
                            ),
                          ),
                          Container(
                            width: 80,
                            height: 80,
                            decoration: const BoxDecoration(
                              shape: BoxShape.circle,
                              gradient: RadialGradient(
                                colors: [
                                  Color(0xFF818CF8),
                                  Color(0xFF4F46E5),
                                  Color(0xFF312E81),
                                ],
                                stops: [0.2, 0.7, 1.0],
                              ),
                            ),
                          ),
                          Container(
                            width: 25,
                            height: 25,
                            decoration: BoxDecoration(
                              shape: BoxShape.circle,
                              color: Colors.white.withValues(alpha: 0.5),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 32),

              // 3. TODAY'S FOCUS Section
              const Text(
                "TODAY'S FOCUS",
                style: TextStyle(
                  fontSize: 11,
                  fontWeight: FontWeight.w800,
                  color: AppColors.textSecondary,
                  letterSpacing: 1.2,
                ),
              ),
              const SizedBox(height: 12),
              SingleChildScrollView(
                scrollDirection: Axis.horizontal,
                child: Row(
                  children: [
                    _buildFocusPill('🚀 Q3 Launch Strategy', const Color(0xFF6366F1), true),
                    const SizedBox(width: 8),
                    _buildFocusPill('💪 Health Vitality', const Color(0xFF10B981), false),
                    const SizedBox(width: 8),
                    _buildFocusPill('📊 Budget Cap Review', const Color(0xFFF59E0B), false),
                  ],
                ),
              ),
              const SizedBox(height: 28),

              // 4. Life Operating Score Section
              const Text(
                'LIFE OPERATING SCORE',
                style: TextStyle(
                  fontSize: 11,
                  fontWeight: FontWeight.w800,
                  color: AppColors.textSecondary,
                  letterSpacing: 1.2,
                ),
              ),
              const SizedBox(height: 12),
              GlassContainer(
                padding: const EdgeInsets.all(20),
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        const Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text('Overall Life Score', style: TextStyle(fontSize: 14, color: AppColors.textSecondary)),
                            SizedBox(height: 4),
                            Text('88 / 100', style: TextStyle(fontSize: 26, fontWeight: FontWeight.bold, color: AppColors.textPrimary)),
                          ],
                        ),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                          decoration: BoxDecoration(
                            color: const Color(0xFF10B981).withValues(alpha: 0.15),
                            borderRadius: BorderRadius.circular(20),
                          ),
                          child: const Row(
                            children: [
                              Icon(Icons.trending_up, color: Color(0xFF10B981), size: 16),
                              SizedBox(width: 4),
                              Text('+4% this week', style: TextStyle(color: Color(0xFF10B981), fontSize: 12, fontWeight: FontWeight.bold)),
                            ],
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 16),
                    const Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        _ScoreMetric(label: 'Productivity', value: '90%'),
                        _ScoreMetric(label: 'Health', value: '92%'),
                        _ScoreMetric(label: 'Finance', value: '85%'),
                        _ScoreMetric(label: 'Learning', value: '88%'),
                      ],
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 28),

              // 5. Proactive AI Insights
              const Text(
                'PROACTIVE INSIGHTS',
                style: TextStyle(
                  fontSize: 11,
                  fontWeight: FontWeight.w800,
                  color: AppColors.textSecondary,
                  letterSpacing: 1.2,
                ),
              ),
              const SizedBox(height: 12),
              _buildInsightCard('Productivity Velocity', 'You completed 93% of planned work this week. Finishing the backend API today will keep your project on schedule.', Icons.auto_awesome),
              const SizedBox(height: 10),
              _buildInsightCard('Budget Variance Alert', 'Your monthly spending has increased by 18% compared to last month. Dining expenses account for most of the increase.', Icons.account_balance_wallet_outlined),
              const SizedBox(height: 28),

              // 6. Schedule Timeline Preview
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text(
                    'SCHEDULE TIMELINE',
                    style: TextStyle(
                      fontSize: 11,
                      fontWeight: FontWeight.w800,
                      color: AppColors.textSecondary,
                      letterSpacing: 1.2,
                    ),
                  ),
                  TextButton(
                    onPressed: () => context.go('/domains'),
                    child: const Text('View All', style: TextStyle(color: Color(0xFF6366F1), fontSize: 12, fontWeight: FontWeight.bold)),
                  ),
                ],
              ),
              const SizedBox(height: 8),
              _buildTimelineItem('09:30 AM', 'Phase 9 AI Dashboard Certification', 'Priority Task', true),
              const SizedBox(height: 8),
              _buildTimelineItem('02:00 PM', 'Project Roadmap Review', 'Meeting with Team', false),
              const SizedBox(height: 8),
              _buildTimelineItem('06:00 PM', 'Health Vitality & Sleep Tracking', 'Habit Routine', false),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildFocusPill(String text, Color color, bool selected) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
      decoration: BoxDecoration(
        color: selected ? color.withValues(alpha: 0.15) : AppColors.surface,
        borderRadius: BorderRadius.circular(20),
        border: Border.all(color: selected ? color : Colors.transparent, width: 1.5),
      ),
      child: Text(
        text,
        style: TextStyle(color: selected ? color : AppColors.textPrimary, fontSize: 13, fontWeight: FontWeight.w600),
      ),
    );
  }

  Widget _buildInsightCard(String title, String desc, IconData icon) {
    return GlassContainer(
      padding: const EdgeInsets.all(16),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            padding: const EdgeInsets.all(10),
            decoration: BoxDecoration(
              color: const Color(0xFF6366F1).withValues(alpha: 0.15),
              borderRadius: BorderRadius.circular(12),
            ),
            child: Icon(icon, color: const Color(0xFF6366F1), size: 20),
          ),
          const SizedBox(width: 14),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(title, style: const TextStyle(fontSize: 14, fontWeight: FontWeight.bold, color: AppColors.textPrimary)),
                const SizedBox(height: 4),
                Text(desc, style: const TextStyle(fontSize: 12, color: AppColors.textSecondary, height: 1.4)),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildTimelineItem(String time, String title, String category, bool active) {
    return Container(
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: active ? const Color(0xFF6366F1).withValues(alpha: 0.1) : AppColors.surface,
        borderRadius: BorderRadius.circular(14),
        border: Border.all(color: active ? const Color(0xFF6366F1) : Colors.transparent),
      ),
      child: Row(
        children: [
          Text(time, style: TextStyle(color: active ? const Color(0xFF6366F1) : AppColors.textSecondary, fontSize: 12, fontWeight: FontWeight.bold)),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(title, style: const TextStyle(color: AppColors.textPrimary, fontSize: 14, fontWeight: FontWeight.w600)),
                const SizedBox(height: 2),
                Text(category, style: const TextStyle(color: AppColors.textSecondary, fontSize: 11)),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class _ScoreMetric extends StatelessWidget {
  final String label;
  final String value;
  const _ScoreMetric({required this.label, required this.value});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Text(value, style: const TextStyle(fontSize: 15, fontWeight: FontWeight.bold, color: AppColors.textPrimary)),
        const SizedBox(height: 2),
        Text(label, style: const TextStyle(fontSize: 11, color: AppColors.textSecondary)),
      ],
    );
  }
}
