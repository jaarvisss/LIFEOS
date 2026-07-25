import 'dart:async';

class SocketClient {
  final StreamController<String> _streamController = StreamController<String>.broadcast();

  Stream<String> get sseStream => _streamController.stream;

  void connect(String endpoint) {
    // SSE websocket connection handler
  }

  void disconnect() {
    _streamController.close();
  }
}
