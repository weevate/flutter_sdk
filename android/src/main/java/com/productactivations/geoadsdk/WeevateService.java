package com.productactivations.geoadsdk;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

    public class WeevateService extends Service {

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate(){
           EasyLogger.toast(this, "Service created");
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startid) {
            EasyLogger.toast(this, "ONstart service");
            return Service.START_STICKY;
        }



        @Override
        public void onDestroy(){

        }
    }
