import 'package:flutter/material.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../design_system/primitives/glass_container.dart';

class DomainHubScreen extends StatelessWidget {
  const DomainHubScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        title: const Text('Life Domains'),
        backgroundColor: AppColors.surface,
        elevation: 0,
      ),
      body: GridView.count(
        crossAxisCount: 2,
        padding: const EdgeInsets.all(20),
        crossAxisSpacing: 16,
        mainAxisSpacing: 16,
        children: const [
          _DomainCard(
            title: 'Tasks & Goals',
            subtitle: '8 Active Tasks',
            icon: Icons.check_circle_outline,
            color: AppColors.domainTasks,
          ),
          _DomainCard(
            title: 'Calendar & Events',
            subtitle: '3 Events Today',
            icon: Icons.calendar_today_outlined,
            color: AppColors.domainCalendar,
          ),
          _DomainCard(
            title: 'Finance & Budgets',
            subtitle: '\$2,450 / \$3k Spent',
            icon: Icons.account_balance_wallet_outlined,
            color: AppColors.domainFinance,
          ),
          _DomainCard(
            title: 'Health & Vitality',
            subtitle: '7.8 hrs Sleep • 84% Vitals',
            icon: Icons.favorite_border,
            color: AppColors.domainHealth,
          ),
          _DomainCard(
            title: 'Knowledge Base',
            subtitle: '142 Notes Indexed',
            icon: Icons.lightbulb_outline,
            color: AppColors.domainKnowledge,
          ),
          _DomainCard(
            title: 'People & CRM',
            subtitle: '18 Contacts',
            icon: Icons.people_outline,
            color: AppColors.domainPeople,
          ),
        ],
      ),
    );
  }
}

class _DomainCard extends StatelessWidget {
  final String title;
  final String subtitle;
  final IconData icon;
  final Color color;

  const _DomainCard({
    required this.title,
    required this.subtitle,
    required this.icon,
    required this.color,
  });

  @override
  Widget build(BuildContext context) {
    return GlassContainer(
      padding: const EdgeInsets.all(16),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            padding: const EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: color.withValues(alpha: 0.12),
              borderRadius: BorderRadius.circular(14),
            ),
            child: Icon(icon, color: color, size: 28),
          ),
          const Spacer(),
          Text(
            title,
            style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 15, color: AppColors.textPrimary),
          ),
          const SizedBox(height: 4),
          Text(
            subtitle,
            style: const TextStyle(fontSize: 11, color: AppColors.textSecondary),
          ),
        ],
      ),
    );
  }
}
