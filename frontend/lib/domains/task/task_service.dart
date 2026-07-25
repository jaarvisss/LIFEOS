import 'task_model.dart';

class TaskService {
  Future<List<TaskModel>> fetchTasks() async {
    // API integration point for backend tasks endpoint
    return [
      TaskModel(id: '1', title: 'Review LifeOS Flutter Architecture', priority: 'HIGH'),
      TaskModel(id: '2', title: 'Complete AI Context Engine Setup', priority: 'HIGH'),
    ];
  }

  Future<TaskModel> createTask(String title) async {
    return TaskModel(id: DateTime.now().millisecondsSinceEpoch.toString(), title: title);
  }
}
