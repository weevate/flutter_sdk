import 'dart:async';

import 'package:flutter/services.dart';
import 'package:location/location.dart';
class WeevateFlutterSdk {
  static const MethodChannel _channel =
      const MethodChannel('weevate_flutter_sdk');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }



  static Future<void> get initializeWeevate async {

    Location location = new Location();

    bool _serviceEnabled;
    PermissionStatus _permissionGranted;
    LocationData _locationData;

    _serviceEnabled = await location.serviceEnabled();
    if (!_serviceEnabled) {
      _serviceEnabled = await location.requestService();
      if (!_serviceEnabled) {
        return 'location not turned on';
      }
    }

    _permissionGranted = await location.hasPermission();
    if (_permissionGranted == PermissionStatus.denied) {
      _permissionGranted = await location.requestPermission();
      if (_permissionGranted != PermissionStatus.granted) {
        return 'location permission not granted';
      }
    }

    await _channel.invokeMethod('startWeevate');
  }
}
