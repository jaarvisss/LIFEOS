class GoalModel {
  final String id;
  final String title;
  final String category;
  final double progress; // 0.0 to 1.0
  final DateTime targetDate;

  GoalModel({
    required this.id,
    required this.title,
    required this.category,
    required this.progress,
    required this.targetDate,
  });
}
