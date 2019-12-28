package com.ygaps.travelapp.network;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.model.LatLng;

public class LocationService extends Service{

    private LatLng currentPosition=new LatLng(10.762966, 106.682172);
    private boolean isContinue=true;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentPosition=new LatLng(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //check permission
        if (ContextCompat.checkSelfPermission(LocationService.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
            1, mLocationListener);
        }

        Thread serviceThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (isContinue){
                    try{
                        Thread.sleep(1000);
                        sendMessageToActivity();
                    } catch (Exception e){
                        Log.d("LocationService",e.getMessage());
                    }
                }
            }
        });

        serviceThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    private void sendMessageToActivity() {
        Intent intent = new Intent("GPSLocationUpdates");

        intent.putExtra("lat", currentPosition.latitude);
        intent.putExtra("long",currentPosition.longitude);

        LocalBroadcastManager.getInstance(LocationService.this).sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isContinue=false;
    }
}
