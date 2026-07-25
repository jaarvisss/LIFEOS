class FinanceTransactionModel {
  final String id;
  final String description;
  final double amount;
  final String category;
  final DateTime timestamp;

  FinanceTransactionModel({
    required this.id,
    required this.description,
    required this.amount,
    required this.category,
    required this.timestamp,
  });
}
