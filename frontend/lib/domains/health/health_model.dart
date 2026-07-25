class HealthMetricModel {
  final String id;
  final String metricType; // sleep, heart_rate, steps, water
  final double value;
  final String unit;
  final DateTime recordedAt;

  HealthMetricModel({
    required this.id,
    required this.metricType,
    required this.value,
    required this.unit,
    required this.recordedAt,
  });
}
