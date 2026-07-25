import 'package:hive_flutter/hive_flutter.dart';

class HiveCacheService {
  static Future<void> init() async {
    await Hive.initFlutter();
  }

  Future<void> put(String boxName, String key, dynamic value) async {
    final box = await Hive.openBox(boxName);
    await box.put(key, value);
  }

  Future<dynamic> get(String boxName, String key) async {
    final box = await Hive.openBox(boxName);
    return box.get(key);
  }

  Future<void> delete(String boxName, String key) async {
    final box = await Hive.openBox(boxName);
    await box.delete(key);
  }
}
