import 'task_model.dart';

class TaskDto {
  final String id;
  final String title;
  final String? description;
  final bool isCompleted;
  final String priority;

  TaskDto({
    required this.id,
    required this.title,
    this.description,
    required this.isCompleted,
    required this.priority,
  });

  factory TaskDto.fromJson(Map<String, dynamic> json) {
    return TaskDto(
      id: json['id'],
      title: json['title'],
      description: json['description'],
      isCompleted: json['is_completed'] ?? false,
      priority: json['priority'] ?? 'MEDIUM',
    );
  }

  TaskModel toDomain() {
    return TaskModel(
      id: id,
      title: title,
      description: description,
      isCompleted: isCompleted,
      priority: priority,
    );
  }
}
