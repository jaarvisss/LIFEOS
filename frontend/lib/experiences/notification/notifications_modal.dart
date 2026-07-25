import 'package:flutter/material.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../design_system/primitives/glass_container.dart';

class NotificationsModal extends StatelessWidget {
  const NotificationsModal({super.key});

  @override
  Widget build(BuildContext context) {
    final List<Map<String, String>> notifications = [
      {
        'title': 'AI Daily Briefing Ready',
        'body': 'Your priorities for today have been synthesized.',
        'time': '10 mins ago',
        'type': 'ai'
      },
      {
        'title': 'Task Focus Reminder',
        'body': 'Review Flutter architecture plan before afternoon meeting.',
        'time': '1 hour ago',
        'type': 'task'
      },
      {
        'title': 'Finance Budget Alert',
        'body': 'You reached 81% of your monthly entertainment budget.',
        'time': '3 hours ago',
        'type': 'finance'
      },
    ];

    return Padding(
      padding: EdgeInsets.only(
        bottom: MediaQuery.of(context).viewInsets.bottom,
      ),
      child: GlassContainer(
        borderRadius: const BorderRadius.vertical(top: Radius.circular(24)),
        color: AppColors.surface,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Row(
                  children: [
                    Icon(Icons.notifications_active_outlined, color: AppColors.primary, size: 22),
                    SizedBox(width: 8),
                    Text(
                      'Notifications & AI Briefs',
                      style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: AppColors.textPrimary),
                    ),
                  ],
                ),
                IconButton(
                  icon: const Icon(Icons.close, color: AppColors.textSecondary),
                  onPressed: () => Navigator.of(context).pop(),
                ),
              ],
            ),
            const SizedBox(height: 16),
            ListView.separated(
              shrinkWrap: true,
              itemCount: notifications.length,
              separatorBuilder: (context, index) => const SizedBox(height: 10),
              itemBuilder: (context, index) {
                final item = notifications[index];
                return Container(
                  padding: const EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: AppColors.backgroundSecondary,
                    borderRadius: BorderRadius.circular(14),
                  ),
                  child: Row(
                    children: [
                      Container(
                        padding: const EdgeInsets.all(8),
                        decoration: const BoxDecoration(
                          color: AppColors.primaryLight,
                          shape: BoxShape.circle,
                        ),
                        child: const Icon(Icons.auto_awesome, color: AppColors.primary, size: 18),
                      ),
                      const SizedBox(width: 12),
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              item['title']!,
                              style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 13, color: AppColors.textPrimary),
                            ),
                            const SizedBox(height: 2),
                            Text(
                              item['body']!,
                              style: const TextStyle(fontSize: 12, color: AppColors.textSecondary),
                            ),
                          ],
                        ),
                      ),
                      Text(
                        item['time']!,
                        style: const TextStyle(fontSize: 10, color: AppColors.textSecondary),
                      ),
                    ],
                  ),
                );
              },
            ),
            const SizedBox(height: 16),
          ],
        ),
      ),
    );
  }
}
