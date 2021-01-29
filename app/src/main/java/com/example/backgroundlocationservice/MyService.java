package com.example.backgroundlocationservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MyService extends Service {
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 5 * 1000;  /*5 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    DatabaseReference myref;
    HashMap<String,String>hashMap= new HashMap<>();
    public MyService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationactivity();
        return START_STICKY;
    }

    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    @Override
    public void onDestroy() {
       super.onDestroy();
    }

    private void locationactivity() {
        //Toast.makeText(this, "come", Toast.LENGTH_SHORT).show();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }

                }, Looper.getMainLooper());
    }

    private void onLocationChanged(Location lastLocation) {
        Toast.makeText(this, "come", Toast.LENGTH_SHORT).show();
        myref=FirebaseDatabase.getInstance().getReference("users");
              hashMap.put("latitude",Double.toString(lastLocation.getLatitude()));  //Double.toString(lastLocation.getLatitude());
              hashMap.put("longitude",Double.toString(lastLocation.getLongitude()));
         myref.child("location").setValue(hashMap);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
