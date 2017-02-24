package com.example.ruby.mygetgps.services.prev_trip;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import timber.log.Timber;

/**
 * StartTripService's builder which encloses all connection with GoogleApiClient
 *
 * @see com.example.ruby.mygetgps.services.GeofenceTransitionsIntentService
 * @see GoogleApiClient
 */
public class StartTripBuilder implements ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<Status> {

    private final Context mContext;
    private final GoogleApiClient mGoogleApiClient;
    private PendingIntent mPendingIntent;
    private Location mLastKnowLocation;
    private PendingResult<Status> pendingResult;

    private final GeofencingApi geofencingApi;
    private final ActivityRecognitionApi activityRecognitionApi;

    private static StartTripBuilder instance;

    /**
     * Returns the current StartTripBuilder instance in the app
     *
     * @return current StartTripBuilder
     */
    public static StartTripBuilder getInstance() {
        Timber.d("method=StartTripBuilder instance='%s'", instance != null ? instance.toString() : "NO");
        return instance;
    }

    /**
     * Adds connection with GoogleApiClient
     *
     * @param context           context of whoever called the receiver
     * @param mLastKnowLocation last location registered from the user
     * @see com.google.android.gms.common.api.GoogleApiClient.Builder#addApi(Api)
     */
    public StartTripBuilder(Context context, Location mLastKnowLocation) {
        instance = this;
        mContext = context;
        mPendingIntent = null;
        this.mLastKnowLocation = mLastKnowLocation;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).addApi(ActivityRecognition.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        geofencingApi = LocationServices.GeofencingApi;
        activityRecognitionApi = ActivityRecognition.ActivityRecognitionApi;
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(Status status) {

    }
}
