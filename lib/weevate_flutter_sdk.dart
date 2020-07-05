import 'dart:async';

import 'package:flutter/services.dart';

class WeevateFlutterSdk {
  static const MethodChannel _channel =
      const MethodChannel('weevate_flutter_sdk');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }


  static Future<String> get startWeevate async {
    await _channel.invokeMethod('startWeevate');
  }
}
