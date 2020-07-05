package com.weevate.weevate_flutter_sdk;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.productactivations.geoadsdk.AlarmReceiver;
import com.productactivations.geoadsdk.EasyLogger;
import com.productactivations.geoadsdk.ProductActivations;
import com.productactivations.geoadsdk.Utility;
import com.productactivations.geoadsdk.WeevateService;

/** WeevateFlutterSdkPlugin */
public class WeevateFlutterSdkPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context context;
  private Activity activity;


    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1)
                .setFastestInterval(1)
                .setMaxWaitTime(1)
                .setSmallestDisplacement(1);

        return locationRequest;
    }


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "weevate_flutter_sdk");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();

  }

  private void setupAlarm(int seconds) {

    //Utility.scheduleJob(context);
    EasyLogger.toast(context, " Set alarms");
    //context.startService(new Intent(context, WeevateService.class));
  }

  int count = 0;
  private void getLocation(){
      EasyLogger.toast(context,"Getting locations");
      LocationRequest mLocationRequest = createLocationRequest();

      final FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

      mFusedLocationClient.requestLocationUpdates(
              mLocationRequest, new LocationCallback(){
                  @Override
                  public void onLocationResult(LocationResult locationResult) {
                      final Location mLastLocation = locationResult.getLastLocation();
                      count++;
                      EasyLogger.toast(context,  + count+ " Lat: " + mLastLocation.getLatitude() + ", long " + mLastLocation.getLongitude());
               }
              },
              Looper.myLooper()
      );
  }



  public void startWeevate(){
    //setupAlarm(10);
    //ProductActivations.getInstance(context).initialize();
   // ProductActivations.getInstance(context).onPermissionGranted();

  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "weevate_flutter_sdk");
    channel.setMethodCallHandler(new WeevateFlutterSdkPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {

      result.success("Android " + android.os.Build.VERSION.RELEASE);

    }
   else if(call.method.equals("startWeevate")){
       EasyLogger.toast(context,"Calling get location");
        getLocation();
    }
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
