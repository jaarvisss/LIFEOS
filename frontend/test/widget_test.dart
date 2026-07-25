import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:lifeos_frontend/main.dart';

void main() {
  testWidgets('LifeOsApp initial render test', (WidgetTester tester) async {
    await tester.pumpWidget(
      const ProviderScope(
        child: LifeOsApp(),
      ),
    );
    await tester.pump(const Duration(seconds: 3));
    expect(find.byType(LifeOsApp), findsOneWidget);
  });
}
