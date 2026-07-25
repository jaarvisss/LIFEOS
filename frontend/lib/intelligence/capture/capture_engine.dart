import '../intent/intent_classifier.dart';

class CaptureEngine {
  final IntentClassifier _classifier = IntentClassifier();

  Future<CapturedIntent> processInput(String rawInput) async {
    final intent = await _classifier.classify(rawInput);
    return intent;
  }
}
