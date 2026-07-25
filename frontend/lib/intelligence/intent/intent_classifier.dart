enum TargetDomain { task, calendar, finance, health, knowledge, people, unknown }

class CapturedIntent {
  final String rawInput;
  final TargetDomain domain;
  final double confidenceScore;
  final Map<String, dynamic> extractedEntities;

  CapturedIntent({
    required this.rawInput,
    required this.domain,
    required this.confidenceScore,
    required this.extractedEntities,
  });
}

class IntentClassifier {
  Future<CapturedIntent> classify(String input) async {
    // Basic heuristic intent classification fallback
    final lower = input.toLowerCase();
    TargetDomain domain = TargetDomain.unknown;
    if (lower.contains('task') || lower.contains('todo') || lower.contains('remind')) {
      domain = TargetDomain.task;
    } else if (lower.contains('buy') || lower.contains('\$') || lower.contains('paid')) {
      domain = TargetDomain.finance;
    } else if (lower.contains('meet') || lower.contains('doctor') || lower.contains('workout')) {
      domain = TargetDomain.health;
    }

    return CapturedIntent(
      rawInput: input,
      domain: domain,
      confidenceScore: 0.90,
      extractedEntities: {},
    );
  }
}
