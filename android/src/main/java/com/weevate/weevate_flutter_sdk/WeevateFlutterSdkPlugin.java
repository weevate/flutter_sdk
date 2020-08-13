package com.weevate.weevate_flutter_sdk;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Build;
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
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
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



  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
      Log.d(TAG, "Actual onattachedtoEngine");
      channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "weevate_flutter_sdk");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();

  }


  public void onAttachedToEngine(Context context, Activity activity){
      Log.d(TAG, "custom on attached to engine");
      this.context = context;
      this.activity = activity;
  }

  public void startWeevate(Context ctx){
    Log.d(TAG, "Start weevate");
    ProductActivations.getInstance(ctx).initialize();
    registerJob();

  }


  public void registerJob(){

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          Utility.scheduleJob(context);
          EasyLogger.toast(context, "Scheduled job");
      }
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

    String TAG  = "WEEVATE";
  public static void registerWith(Registrar registrar) {

      Log.d("WEEVATE", "Register with");
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "weevate_flutter_sdk");
    WeevateFlutterSdkPlugin plugin = new WeevateFlutterSdkPlugin();
    plugin.onAttachedToEngine(registrar.context(), registrar.activity());
    channel.setMethodCallHandler(plugin);

  }


  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

      Log.d(TAG, "on method call");

    if (call.method.equals("getPlatformVersion")) {

      result.success("Android " + android.os.Build.VERSION.RELEASE);

    }
   else if(call.method.equals("startWeevate")){
       EasyLogger.toast(context,"Starting Weevate");
        startWeevate(context);

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
