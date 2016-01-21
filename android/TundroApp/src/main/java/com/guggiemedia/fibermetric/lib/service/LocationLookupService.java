package com.guggiemedia.fibermetric.lib.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.guggiemedia.fibermetric.lib.chain.CommandEnum;
import com.guggiemedia.fibermetric.lib.chain.CommandFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextFactory;
import com.guggiemedia.fibermetric.lib.chain.GeographicUpdateCtx;
import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;


/**
 * Re-implementation of location service using new Google API
 */
public class LocationLookupService extends Service
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String LOG_TAG = LocationLookupService.class.getName();

    private static int PRIORITY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY; // conserve battery
    private static long INTERVAL = 10000; // 10 seconds
    private static long FASTEST_INTERVAL = 5000; // 5 seconds

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(PRIORITY);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(LOG_TAG, "onConnected");

        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "onConnectionFailed");
    }

    // locationlistener

    @Override
    public void onLocationChanged(Location location) {
        Location testLocation = getTestLocation();

      //  Personality2.currentLocation = (testLocation == null) ? location : testLocation;

        GeographicUpdateCtx updateCtx = (GeographicUpdateCtx) ContextFactory.factory(CommandEnum.GEOGRAPHIC_UPDATE, this);
        if (testLocation == null) {
            updateCtx.setTestFlag(false);
            updateCtx.setLocation(location);
        } else {
            updateCtx.setTestFlag(true);
            updateCtx.setLocation(testLocation);
        }

        CommandFactory.execute(updateCtx);
    }

    /**
     * @return
     */
    private Location getTestLocation() {
        Location location = null;

        UserPreferenceHelper uph = new UserPreferenceHelper();

        Float testLatitude = uph.getTestUserLatitude(this);
        Float testLongitude = uph.getTestUserLongitude(this);

        if (testLatitude != 0 && testLongitude != 0) {
            location = new Location("TEST LOCATION");

            location.setLatitude(testLatitude);
            location.setLongitude(testLongitude);

            Log.w(LOG_TAG, "Using test location: " + location);

            // stop location updates
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        return location;
    }
}
