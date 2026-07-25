class TaskModel {
  final String id;
  final String title;
  final String? description;
  final bool isCompleted;
  final String priority;
  final DateTime? dueDate;

  TaskModel({
    required this.id,
    required this.title,
    this.description,
    this.isCompleted = false,
    this.priority = 'MEDIUM',
    this.dueDate,
  });

  factory TaskModel.fromJson(Map<String, dynamic> json) {
    return TaskModel(
      id: json['id'],
      title: json['title'],
      description: json['description'],
      isCompleted: json['is_completed'] ?? false,
      priority: json['priority'] ?? 'MEDIUM',
      dueDate: json['due_date'] != null ? DateTime.parse(json['due_date']) : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'is_completed': isCompleted,
      'priority': priority,
      'due_date': dueDate?.toIso8601String(),
    };
  }
}
