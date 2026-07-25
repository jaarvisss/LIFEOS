import 'task_model.dart';
import 'task_service.dart';

abstract class ITaskRepository {
  Future<List<TaskModel>> getTasks();
  Future<TaskModel> createTask(String title);
}

class TaskRepository implements ITaskRepository {
  final TaskService _service;
  TaskRepository(this._service);

  @override
  Future<List<TaskModel>> getTasks() async {
    return await _service.fetchTasks();
  }

  @override
  Future<TaskModel> createTask(String title) async {
    return await _service.createTask(title);
  }
}
