import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:weevate_flutter_sdk/weevate_flutter_sdk.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    WeevateFlutterSdk.initializeWeevate;

    return MaterialApp(
         home: Scaffold(
      appBar: AppBar (
       title: Text("Hello Larry"),
    )
    ));
  }
}