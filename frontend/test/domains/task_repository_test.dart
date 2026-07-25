import 'package:flutter_test/flutter_test.dart';
import 'package:lifeos_frontend/domains/task/task_service.dart';
import 'package:lifeos_frontend/domains/task/task_repository.dart';

void main() {
  group('TaskRepository Tests', () {
    late TaskService service;
    late TaskRepository repository;

    setUp(() {
      service = TaskService();
      repository = TaskRepository(service);
    });

    test('getTasks returns a non-empty list of tasks', () async {
      final tasks = await repository.getTasks();
      expect(tasks, isNotEmpty);
      expect(tasks.first.title, contains('LifeOS'));
    });

    test('createTask creates a task with title', () async {
      final task = await repository.createTask('Test New Task');
      expect(task.title, equals('Test New Task'));
    });
  });
}
