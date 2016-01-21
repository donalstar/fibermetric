package com.guggiemedia.fibermetric.lib.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.Personality;


/**
 * collect location updates
 *
 * @deprecated (maybe) - @see LocationLookupService (using new Google API)
 */
public class LocationReportService extends Service implements LocationListener {
    public static final String LOG_TAG = LocationReportService.class.getName();

    // LocationListener
    public void onLocationChanged(Location location) {
        Log.d(LOG_TAG, "xxx xxx Location Change:" + location.toString());

        Personality.currentLocation = location;
    }

    // LocationListener
    public void onProviderDisabled(String provider) {
        Log.d(LOG_TAG, "xxx xxx provider disabled:" + provider);
    }

    // LocationListener
    public void onProviderEnabled(String provider) {
        Log.d(LOG_TAG, "xxx xxx provider enabled:" + provider);
    }

    // LoctionListener
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(LOG_TAG, "xxx xxx onStatusChanged:" + provider + ":" + status + ":" + extras.toString());

/*
        Location location = lm.getLastKnownLocation(provider);
        Log.d(LOG_TAG, provider + ":" + location);

        if (extras.isEmpty()) {
            Log.d(LOG_TAG, "xxx xxx empty bundle xxx xxx");
        } else {
            Log.d(LOG_TAG, "xxx xxx bundle size:" + extras.size());

            Set<String> keyz = extras.keySet();
            Iterator<String> ii = keyz.iterator();
            while (ii.hasNext()) {
                String key = ii.next();
                Log.d(LOG_TAG, "xxx xxx extra key:" + key);
            }

            Log.d(LOG_TAG, "xxx xxx population:" + extras.getInt("satellites"));
        }
        */
    }

    public LocationReportService() {
        //empty
    }

    @Override
    public void onConfigurationChanged(Configuration arg) {
        Log.d(LOG_TAG, "xxx xxx onConfigurationChanged xxx xxx ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.w(LOG_TAG, "xxx xxx OnStart/" + startId + " xxx xxx");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "xxx xxx onCreate xxx xxx ");

        int one_km = 1000;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 12345, one_km, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 12345, one_km, this);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "xxx xxx onDestroy xxx xxx ");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
