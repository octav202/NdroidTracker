package com.ndroid.ndroidtracker;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ndroid.ndroidtracker.models.DeviceLocation;
import com.ndroid.ndroidtracker.server.SendLocationTask;
import com.ndroid.ndroidtracker.server.ServerApi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AntiTheftService extends Service {

    private static final String TAG = "NT_Service";
    private LocationManager mLocationManager;
    private int LOCATION_REFRESH_TIME = 3000;
    private int LOCATION_REFRESH_DISTANCE = 50;

    // Send location
    private Handler mHandler;
    private Runnable mGetLockStatusRunnable = new Runnable() {
        @Override
        public void run() {
            if (ActivityCompat.checkSelfPermission(AntiTheftService.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(AntiTheftService.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // REQUEST LOCATION PERMISSION
                return;
            }

            // Get Current DeviceLocation
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // Get Current Time
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(new Date());

            // DeviceLocation to send to server
            DeviceLocation devLoc = new DeviceLocation();
            devLoc.setDeviceId(ServerApi.getCurrentDeviceId());
            devLoc.setLat(location.getLatitude());
            devLoc.setLon(location.getLongitude());
            devLoc.setTimeStamp(time);

            new SendLocationTask(new SendLocationTask.SendLocationCallback() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onFinished(Boolean result) {

                }
            }).execute(devLoc);

            mHandler.postDelayed(this, LOCATION_REFRESH_TIME);
        }
    };


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Log.d(TAG,"onLocationChanged() " + location.getLatitude() + ", " +
            location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");

        mHandler = new Handler();
        mHandler.postDelayed(mGetLockStatusRunnable, LOCATION_REFRESH_TIME);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
        } else {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
