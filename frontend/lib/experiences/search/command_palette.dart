import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../design_system/primitives/glass_container.dart';

class CommandPaletteModal extends StatefulWidget {
  const CommandPaletteModal({super.key});

  @override
  State<CommandPaletteModal> createState() => _CommandPaletteModalState();
}

class _CommandPaletteModalState extends State<CommandPaletteModal> {
  final TextEditingController _queryController = TextEditingController();
  String _searchQuery = '';

  final List<Map<String, dynamic>> _commands = [
    {'title': 'Open Home Cockpit', 'route': '/', 'icon': Icons.home_rounded, 'category': 'Navigation'},
    {'title': 'Open AI Companion', 'route': '/companion', 'icon': Icons.auto_awesome_rounded, 'category': 'Navigation'},
    {'title': 'Open Life Domains', 'route': '/domains', 'icon': Icons.grid_view_rounded, 'category': 'Navigation'},
    {'title': 'Open Profile & Settings', 'route': '/profile', 'icon': Icons.person_rounded, 'category': 'Navigation'},
    {'title': 'Create New Priority Task', 'action': 'task', 'icon': Icons.add_task_rounded, 'category': 'Actions'},
    {'title': 'Log Expense or Transaction', 'action': 'finance', 'icon': Icons.account_balance_wallet_outlined, 'category': 'Actions'},
    {'title': 'View Vector Memory Graph', 'route': '/companion', 'icon': Icons.psychology_outlined, 'category': 'AI Engine'},
  ];

  @override
  Widget build(BuildContext context) {
    final filtered = _commands
        .where((cmd) => cmd['title'].toString().toLowerCase().contains(_searchQuery.toLowerCase()))
        .toList();

    return Padding(
      padding: EdgeInsets.only(
        top: 60,
        bottom: MediaQuery.of(context).viewInsets.bottom + 20,
        left: 16,
        right: 16,
      ),
      child: GlassContainer(
        borderRadius: BorderRadius.circular(24),
        color: AppColors.surface,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Search Input Row
            Row(
              children: [
                const Icon(Icons.search_rounded, color: AppColors.primary, size: 22),
                const SizedBox(width: 12),
                Expanded(
                  child: TextField(
                    controller: _queryController,
                    autofocus: true,
                    onChanged: (val) => setState(() => _searchQuery = val),
                    style: const TextStyle(color: AppColors.textPrimary, fontSize: 16),
                    decoration: const InputDecoration(
                      hintText: 'Type a command or search context...',
                      hintStyle: TextStyle(color: AppColors.textSecondary),
                      border: InputBorder.none,
                    ),
                  ),
                ),
                Container(
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                  decoration: BoxDecoration(
                    color: AppColors.backgroundSecondary,
                    borderRadius: BorderRadius.circular(6),
                    border: Border.all(color: AppColors.border),
                  ),
                  child: const Text('ESC', style: TextStyle(fontSize: 10, fontWeight: FontWeight.bold, color: AppColors.textSecondary)),
                ),
              ],
            ),
            const Divider(height: 20, color: AppColors.border),

            // Command Results List
            ConstrainedBox(
              constraints: const BoxConstraints(maxHeight: 320),
              child: filtered.isEmpty
                  ? const Padding(
                      padding: EdgeInsets.all(24.0),
                      child: Center(
                        child: Text('No matching commands found.', style: TextStyle(color: AppColors.textSecondary)),
                      ),
                    )
                  : ListView.builder(
                      shrinkWrap: true,
                      itemCount: filtered.length,
                      itemBuilder: (context, index) {
                        final item = filtered[index];
                        return ListTile(
                          contentPadding: const EdgeInsets.symmetric(horizontal: 8, vertical: 2),
                          leading: Container(
                            padding: const EdgeInsets.all(8),
                            decoration: BoxDecoration(
                              color: AppColors.primaryLight,
                              borderRadius: BorderRadius.circular(10),
                            ),
                            child: Icon(item['icon'] as IconData, color: AppColors.primary, size: 18),
                          ),
                          title: Text(
                            item['title'] as String,
                            style: const TextStyle(fontWeight: FontWeight.w600, fontSize: 14, color: AppColors.textPrimary),
                          ),
                          subtitle: Text(
                            item['category'] as String,
                            style: const TextStyle(fontSize: 11, color: AppColors.textSecondary),
                          ),
                          trailing: const Icon(Icons.arrow_forward_ios_rounded, size: 12, color: AppColors.textSecondary),
                          onTap: () {
                            Navigator.of(context).pop();
                            if (item['route'] != null) {
                              context.go(item['route'] as String);
                            }
                          },
                        );
                      },
                    ),
            ),
          ],
        ),
      ),
    );
  }
}
