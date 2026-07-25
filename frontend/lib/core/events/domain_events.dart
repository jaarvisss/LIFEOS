abstract class DomainEvent {
  final DateTime timestamp = DateTime.now();
}

class TaskCreatedEvent extends DomainEvent {
  final String taskId;
  final String title;
  TaskCreatedEvent({required this.taskId, required this.title});
}

class AICaptureIngestedEvent extends DomainEvent {
  final String captureId;
  final String inferredDomain;
  AICaptureIngestedEvent({required this.captureId, required this.inferredDomain});
}
