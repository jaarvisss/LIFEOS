import 'package:flutter/material.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../design_system/primitives/glass_container.dart';

class CompanionScreen extends StatefulWidget {
  const CompanionScreen({super.key});

  @override
  State<CompanionScreen> createState() => _CompanionScreenState();
}

class _CompanionScreenState extends State<CompanionScreen> {
  final TextEditingController _queryController = TextEditingController();

  final List<Map<String, dynamic>> _quickActions = [
    {
      'title': 'How much can I spend today?',
      'subtitle': 'Get spending insights and suggestions',
      'icon': Icons.account_balance_wallet_rounded,
      'color': const Color(0xFF6366F1),
      'bgColor': const Color(0xFFEEF2FF),
    },
    {
      'title': 'Plan my afternoon',
      'subtitle': "I'll help you plan your day better",
      'icon': Icons.calendar_today_rounded,
      'color': const Color(0xFFF97316),
      'bgColor': const Color(0xFFFFF7ED),
    },
    {
      'title': 'What should I focus on?',
      'subtitle': 'Get clarity on what matters most',
      'icon': Icons.track_changes_rounded,
      'color': const Color(0xFF10B981),
      'bgColor': const Color(0xFFECFDF5),
    },
    {
      'title': 'Check my health progress',
      'subtitle': 'View your activity and wellness summary',
      'icon': Icons.favorite_border_rounded,
      'color': const Color(0xFF06B6D4),
      'bgColor': const Color(0xFFECFEFF),
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Top Header: Sparkle Icon + Title & Subtitle
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20.0, vertical: 16.0),
              child: Row(
                children: [
                  Container(
                    padding: const EdgeInsets.all(10),
                    decoration: BoxDecoration(
                      color: const Color(0xFFEEF2FF),
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: const Icon(Icons.auto_awesome, color: Color(0xFF6366F1), size: 22),
                  ),
                  const SizedBox(width: 14),
                  const Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'AI Companion',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                          color: AppColors.textPrimary,
                          letterSpacing: -0.3,
                        ),
                      ),
                      SizedBox(height: 2),
                      Text(
                        'Your intelligent life partner',
                        style: TextStyle(
                          fontSize: 13,
                          color: AppColors.textSecondary,
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20),

            // Main Query Prompt Title
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 20.0),
              child: Text(
                'How can I help you today?',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                  color: AppColors.textPrimary,
                  letterSpacing: -0.3,
                ),
              ),
            ),
            const SizedBox(height: 20),

            // 4 Quick Action Cards List
            Expanded(
              child: ListView.separated(
                padding: const EdgeInsets.symmetric(horizontal: 20.0),
                itemCount: _quickActions.length,
                separatorBuilder: (context, index) => const SizedBox(height: 14),
                itemBuilder: (context, index) {
                  final action = _quickActions[index];
                  final color = action['color'] as Color;
                  final bgColor = action['bgColor'] as Color;
                  return GlassContainer(
                    padding: const EdgeInsets.all(16),
                    child: Row(
                      children: [
                        Container(
                          padding: const EdgeInsets.all(12),
                          decoration: BoxDecoration(
                            color: bgColor,
                            borderRadius: BorderRadius.circular(14),
                          ),
                          child: Icon(action['icon'] as IconData, color: color, size: 24),
                        ),
                        const SizedBox(width: 16),
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                action['title'] as String,
                                style: const TextStyle(
                                  fontSize: 15,
                                  fontWeight: FontWeight.bold,
                                  color: AppColors.textPrimary,
                                ),
                              ),
                              const SizedBox(height: 3),
                              Text(
                                action['subtitle'] as String,
                                style: const TextStyle(
                                  fontSize: 12,
                                  color: AppColors.textSecondary,
                                ),
                              ),
                            ],
                          ),
                        ),
                        Icon(Icons.chevron_right, color: color, size: 22),
                      ],
                    ),
                  );
                },
              ),
            ),

            // Floating Bottom Input Bar ("Ask anything...")
            Padding(
              padding: const EdgeInsets.all(20.0),
              child: GlassContainer(
                padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
                borderRadius: BorderRadius.circular(20),
                child: Row(
                  children: [
                    const Icon(Icons.auto_awesome, color: Color(0xFF6366F1), size: 20),
                    const SizedBox(width: 12),
                    Expanded(
                      child: TextField(
                        controller: _queryController,
                        style: const TextStyle(color: AppColors.textPrimary, fontSize: 15),
                        decoration: const InputDecoration(
                          hintText: 'Ask anything...',
                          hintStyle: TextStyle(color: AppColors.textSecondary, fontSize: 15),
                          border: InputBorder.none,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
