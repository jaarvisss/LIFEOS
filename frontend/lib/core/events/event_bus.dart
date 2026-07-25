import 'dart:async';

class EventBus {
  static final EventBus _instance = EventBus._internal();
  factory EventBus() => _instance;
  EventBus._internal();

  final StreamController<dynamic> _streamController = StreamController.broadcast();

  Stream<T> on<T>() {
    return _streamController.stream.where((event) => event is T).cast<T>();
  }

  void fire(dynamic event) {
    _streamController.add(event);
  }
}
