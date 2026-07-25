import 'package:flutter/material.dart';
import '../../design_system/tokens/app_colors.dart';

class SettingsScreen extends StatelessWidget {
  const SettingsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        title: const Text('Settings & Security'),
        backgroundColor: AppColors.surface,
        elevation: 0,
      ),
      body: ListView(
        padding: const EdgeInsets.symmetric(vertical: 12),
        children: const [
          ListTile(
            leading: Icon(Icons.security, color: AppColors.primary),
            title: Text('Security & PII Controls', style: TextStyle(fontWeight: FontWeight.w600)),
            subtitle: Text('Biometric auth, zero-knowledge key storage'),
            trailing: Icon(Icons.chevron_right, color: AppColors.textSecondary),
          ),
          Divider(height: 1, color: AppColors.border),
          ListTile(
            leading: Icon(Icons.memory, color: AppColors.primary),
            title: Text('AI Memory Graph', style: TextStyle(fontWeight: FontWeight.w600)),
            subtitle: Text('Manage vector memory indices & data wipes'),
            trailing: Icon(Icons.chevron_right, color: AppColors.textSecondary),
          ),
          Divider(height: 1, color: AppColors.border),
          ListTile(
            leading: Icon(Icons.palette_outlined, color: AppColors.primary),
            title: Text('Theme & Aesthetics', style: TextStyle(fontWeight: FontWeight.w600)),
            subtitle: Text('Modern Pure Light Glassmorphic Theme'),
            trailing: Icon(Icons.chevron_right, color: AppColors.textSecondary),
          ),
        ],
      ),
    );
  }
}
