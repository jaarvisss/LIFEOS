import 'package:flutter/material.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../design_system/primitives/glass_container.dart';
import '../search/command_palette.dart';
import 'domain_detail_modal.dart';

class DomainHubScreen extends StatelessWidget {
  const DomainHubScreen({super.key});

  void _openDomainDetail(
    BuildContext context, {
    required String title,
    required IconData icon,
    required Color color,
    required String subtitle,
    required List<Map<String, String>> items,
  }) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (context) => DomainDetailModal(
        title: title,
        icon: icon,
        color: color,
        subtitle: subtitle,
        items: items,
      ),
    );
  }

  void _openSearch(BuildContext context) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (context) => const CommandPaletteModal(),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 20.0, vertical: 12.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // 1. Header Row: Title | Search Icon | Filter Icon
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text(
                    'Life Domains',
                    style: TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.bold,
                      color: AppColors.textPrimary,
                      letterSpacing: -0.5,
                    ),
                  ),
                  Row(
                    children: [
                      Container(
                        decoration: const BoxDecoration(color: Colors.white, shape: BoxShape.circle),
                        child: IconButton(
                          icon: const Icon(Icons.search_rounded, color: AppColors.textPrimary, size: 20),
                          onPressed: () => _openSearch(context),
                        ),
                      ),
                      const SizedBox(width: 8),
                      Container(
                        decoration: const BoxDecoration(color: Colors.white, shape: BoxShape.circle),
                        child: IconButton(
                          icon: const Icon(Icons.tune_rounded, color: AppColors.textPrimary, size: 20),
                          onPressed: () {},
                        ),
                      ),
                    ],
                  ),
                ],
              ),
              const SizedBox(height: 4),
              const Text(
                'All areas of your life, organized\nand connected.',
                style: TextStyle(fontSize: 14, color: AppColors.textSecondary, height: 1.3),
              ),
              const SizedBox(height: 20),

              // 2. AI Multi-Domain Architecture Banner
              GlassContainer(
                padding: const EdgeInsets.all(16),
                color: const Color(0xFFF5F3FF),
                borderColor: const Color(0xFFDDD6FE),
                child: Row(
                  children: [
                    Container(
                      padding: const EdgeInsets.all(10),
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: const Icon(Icons.auto_awesome, color: Color(0xFF6366F1), size: 22),
                    ),
                    const Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'AI Multi-Domain Architecture',
                            style: TextStyle(fontSize: 13, fontWeight: FontWeight.bold, color: Color(0xFF6366F1)),
                          ),
                          SizedBox(height: 2),
                          Text(
                            'All domains synchronized & active',
                            style: TextStyle(fontSize: 12, color: AppColors.textSecondary),
                          ),
                        ],
                      ),
                    ),
                    const Icon(Icons.chevron_right, color: Color(0xFF6366F1), size: 20),
                  ],
                ),
              ),
              const SizedBox(height: 24),

              // 3. "Your Domains" Title
              const Text(
                'Your Domains',
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: AppColors.textSecondary),
              ),
              const SizedBox(height: 14),

              // 4. Vertical List of Full-Width Domain Cards
              _buildDomainListTile(
                context: context,
                title: 'Tasks & Goals',
                subtitle: '12 tasks • 5 goals',
                badgeText: '● 5 Active',
                badgeColor: const Color(0xFF6366F1),
                icon: Icons.check_circle_outline_rounded,
                iconBg: const Color(0xFFEEF2FF),
                iconColor: const Color(0xFF6366F1),
                onTap: () => _openDomainDetail(
                  context,
                  title: 'Tasks & Goals',
                  icon: Icons.check_circle_outline_rounded,
                  color: const Color(0xFF6366F1),
                  subtitle: '12 tasks • 5 goals active',
                  items: const [
                    {'title': 'Review Flutter Architecture Plan', 'subtitle': 'High Priority • Due 10:30 AM', 'tag': 'Today'},
                    {'title': 'Spring Boot Security Review', 'subtitle': 'High Priority • Due 02:00 PM', 'tag': 'Today'},
                    {'title': 'Weekly Goal Sync', 'subtitle': 'Medium Priority • Tomorrow', 'tag': 'Upcoming'},
                  ],
                ),
              ),
              const SizedBox(height: 12),

              _buildDomainListTile(
                context: context,
                title: 'Calendar & Events',
                subtitle: '3 events today',
                badgeText: '● 3 Today',
                badgeColor: const Color(0xFF0284C7),
                icon: Icons.calendar_today_rounded,
                iconBg: const Color(0xFFE0F2FE),
                iconColor: const Color(0xFF0284C7),
                onTap: () => _openDomainDetail(
                  context,
                  title: 'Calendar & Events',
                  icon: Icons.calendar_today_rounded,
                  color: const Color(0xFF0284C7),
                  subtitle: '3 events scheduled today',
                  items: const [
                    {'title': 'Engineering Standup', 'subtitle': '10:00 AM - 10:30 AM', 'tag': 'Done'},
                    {'title': 'Client Meeting', 'subtitle': '02:00 PM - 03:00 PM', 'tag': 'Next'},
                    {'title': 'Design Review', 'subtitle': '03:30 PM - 04:30 PM', 'tag': 'Scheduled'},
                  ],
                ),
              ),
              const SizedBox(height: 12),

              _buildDomainListTile(
                context: context,
                title: 'Finance & Budgets',
                subtitle: '₹12,450 / ₹18,000 spent',
                badgeText: '● Healthy',
                badgeColor: const Color(0xFF10B981),
                icon: Icons.account_balance_wallet_rounded,
                iconBg: const Color(0xFFECFDF5),
                iconColor: const Color(0xFF10B981),
                onTap: () => _openDomainDetail(
                  context,
                  title: 'Finance & Budgets',
                  icon: Icons.account_balance_wallet_rounded,
                  color: const Color(0xFF10B981),
                  subtitle: '₹12,450 / ₹18,000 spent this month',
                  items: const [
                    {'title': 'Development Server Hosting', 'subtitle': '₹2,500.00 • Cloud Services', 'tag': 'Expense'},
                    {'title': 'Software Subscriptions', 'subtitle': '₹1,200.00 • Productivity', 'tag': 'Expense'},
                    {'title': 'Client Retainer Payment', 'subtitle': '+₹15,000.00 • Revenue', 'tag': 'Income'},
                  ],
                ),
              ),
              const SizedBox(height: 12),

              _buildDomainListTile(
                context: context,
                title: 'Health & Vitality',
                subtitle: '2 workouts • 6k steps',
                badgeText: '● Great',
                badgeColor: const Color(0xFFEC4899),
                icon: Icons.favorite_border_rounded,
                iconBg: const Color(0xFFFCE7F3),
                iconColor: const Color(0xFFEC4899),
                onTap: () => _openDomainDetail(
                  context,
                  title: 'Health & Vitality',
                  icon: Icons.favorite_border_rounded,
                  color: const Color(0xFFEC4899),
                  subtitle: '2 workouts completed • 6,420 steps today',
                  items: const [
                    {'title': 'Morning Run', 'subtitle': '3.2 km • 24 mins', 'tag': 'Workout'},
                    {'title': 'Evening Gym Session', 'subtitle': '45 mins • Strength Training', 'tag': 'Workout'},
                    {'title': 'Daily Step Count', 'subtitle': '6,420 steps • Target 8k', 'tag': 'Active'},
                  ],
                ),
              ),
              const SizedBox(height: 12),

              _buildDomainListTile(
                context: context,
                title: 'Knowledge Base',
                subtitle: '142 notes • 3 pinned',
                badgeText: '● 142 Notes',
                badgeColor: const Color(0xFF8B5CF6),
                icon: Icons.lightbulb_outline_rounded,
                iconBg: const Color(0xFFF3E8FF),
                iconColor: const Color(0xFF8B5CF6),
                onTap: () => _openDomainDetail(
                  context,
                  title: 'Knowledge Base',
                  icon: Icons.lightbulb_outline_rounded,
                  color: const Color(0xFF8B5CF6),
                  subtitle: '142 notes indexed in vector graph',
                  items: const [
                    {'title': 'Clean Architecture Principles', 'subtitle': 'Indexed 2 days ago • Flutter', 'tag': 'Vector'},
                    {'title': 'PostgreSQL Schema Optimizations', 'subtitle': 'Indexed yesterday • Backend', 'tag': 'Vector'},
                    {'title': 'LifeOS System Philosophy', 'subtitle': 'Indexed 3 days ago • Product', 'tag': 'Vector'},
                  ],
                ),
              ),
              const SizedBox(height: 12),

              _buildDomainListTile(
                context: context,
                title: 'People & CRM',
                subtitle: '18 contacts',
                badgeText: '● 18 Contacts',
                badgeColor: const Color(0xFFF59E0B),
                icon: Icons.people_outline_rounded,
                iconBg: const Color(0xFFFEF3C7),
                iconColor: const Color(0xFFF59E0B),
                onTap: () => _openDomainDetail(
                  context,
                  title: 'People & CRM',
                  icon: Icons.people_outline_rounded,
                  color: const Color(0xFFF59E0B),
                  subtitle: '18 active contacts & CRM records',
                  items: const [
                    {'title': 'Sarah Jenkins', 'subtitle': 'Lead Architect • Follow-up tomorrow', 'tag': 'Contact'},
                    {'title': 'Alex Rivera', 'subtitle': 'Product Manager • Last talked yesterday', 'tag': 'Contact'},
                    {'title': 'David Chen', 'subtitle': 'Backend Engineer • Active sync', 'tag': 'Contact'},
                  ],
                ),
              ),
              const SizedBox(height: 20),

              // 5. Explore Insights Bottom Card
              GlassContainer(
                padding: const EdgeInsets.all(16),
                color: const Color(0xFFF8FAFC),
                child: Row(
                  children: [
                    Container(
                      padding: const EdgeInsets.all(10),
                      decoration: BoxDecoration(
                        color: const Color(0xFFEEF2FF),
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: const Icon(Icons.bar_chart_rounded, color: Color(0xFF6366F1), size: 22),
                    ),
                    const Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Explore insights across your domains',
                            style: TextStyle(fontSize: 13, fontWeight: FontWeight.bold, color: AppColors.textPrimary),
                          ),
                          SizedBox(height: 2),
                          Text(
                            'AI-generated insights & recommendations',
                            style: TextStyle(fontSize: 11, color: AppColors.textSecondary),
                          ),
                        ],
                      ),
                    ),
                    Container(
                      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
                      decoration: BoxDecoration(
                        color: const Color(0xFFF5F3FF),
                        borderRadius: BorderRadius.circular(12),
                        border: Border.all(color: const Color(0xFFDDD6FE)),
                      ),
                      child: const Row(
                        children: [
                          Text('View Insights', style: TextStyle(fontSize: 11, fontWeight: FontWeight.bold, color: Color(0xFF6366F1))),
                          Icon(Icons.chevron_right, size: 14, color: Color(0xFF6366F1)),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 24),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildDomainListTile({
    required BuildContext context,
    required String title,
    required String subtitle,
    required String badgeText,
    required Color badgeColor,
    required IconData icon,
    required Color iconBg,
    required Color iconColor,
    required VoidCallback onTap,
  }) {
    return Material(
      color: Colors.transparent,
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(20),
        child: GlassContainer(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 14),
          child: Row(
            children: [
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: iconBg,
                  borderRadius: BorderRadius.circular(16),
                ),
                child: Icon(icon, color: iconColor, size: 24),
              ),
              const SizedBox(width: 16),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      title,
                      style: const TextStyle(fontSize: 15, fontWeight: FontWeight.bold, color: AppColors.textPrimary),
                    ),
                    const SizedBox(height: 3),
                    Text(
                      subtitle,
                      style: const TextStyle(fontSize: 12, color: AppColors.textSecondary),
                    ),
                  ],
                ),
              ),
              Text(
                badgeText,
                style: TextStyle(fontSize: 12, fontWeight: FontWeight.bold, color: badgeColor),
              ),
              const SizedBox(width: 8),
              const Icon(Icons.chevron_right, color: AppColors.textSecondary, size: 20),
            ],
          ),
        ),
      ),
    );
  }
}
