import 'package:flutter/material.dart';
import '../../design_system/tokens/app_colors.dart';
import '../../design_system/primitives/glass_container.dart';
import '../../intelligence/capture/capture_engine.dart';

class UniversalCaptureModal extends StatefulWidget {
  const UniversalCaptureModal({super.key});

  @override
  State<UniversalCaptureModal> createState() => _UniversalCaptureModalState();
}

class _UniversalCaptureModalState extends State<UniversalCaptureModal> {
  final TextEditingController _controller = TextEditingController();
  final CaptureEngine _captureEngine = CaptureEngine();
  bool _isProcessing = false;

  void _submit() async {
    if (_controller.text.trim().isEmpty) return;
    setState(() => _isProcessing = true);
    await _captureEngine.processInput(_controller.text);
    if (mounted) {
      Navigator.of(context).pop();
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Thought captured & synthesized by LifeOS AI.', style: TextStyle(color: Colors.white)),
          backgroundColor: AppColors.primary,
          behavior: SnackBarBehavior.floating,
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
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
                    Icon(Icons.auto_awesome, color: AppColors.primary, size: 22),
                    SizedBox(width: 8),
                    Text(
                      'Universal Capture',
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
            const SizedBox(height: 12),
            TextField(
              controller: _controller,
              autofocus: true,
              maxLines: 4,
              style: const TextStyle(color: AppColors.textPrimary, fontSize: 15),
              decoration: InputDecoration(
                hintText: 'Speak or type any thought, task, expense, or note...',
                hintStyle: const TextStyle(color: AppColors.textSecondary),
                fillColor: AppColors.backgroundSecondary,
                filled: true,
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(16),
                  borderSide: BorderSide.none,
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(16),
                  borderSide: const BorderSide(color: AppColors.primary, width: 1.5),
                ),
              ),
            ),
            const SizedBox(height: 16),
            Row(
              children: [
                IconButton(
                  icon: const Icon(Icons.mic_none_rounded, color: AppColors.primary),
                  onPressed: () {},
                ),
                IconButton(
                  icon: const Icon(Icons.camera_alt_outlined, color: AppColors.primary),
                  onPressed: () {},
                ),
                IconButton(
                  icon: const Icon(Icons.attach_file_rounded, color: AppColors.primary),
                  onPressed: () {},
                ),
                const Spacer(),
                ElevatedButton(
                  onPressed: _isProcessing ? null : _submit,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColors.primary,
                    foregroundColor: Colors.white,
                    elevation: 0,
                    padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(14)),
                  ),
                  child: _isProcessing
                      ? const SizedBox(
                          width: 16,
                          height: 16,
                          child: CircularProgressIndicator(strokeWidth: 2, color: Colors.white),
                        )
                      : const Text('Capture', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 14)),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
