package com.example.ruby.mygetgps.services.on_trip;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.ruby.mygetgps.utils.ConfigurationConstants;
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

import timber.log.Timber;

/**
 * TripTracking's builder which encloses all connection with GoogleApiClient
 *
 * @see GoogleApiClient
 */
public class MotionTrackerBuilder implements ConnectionCallbacks,
        OnConnectionFailedListener, ResultCallback<Status> {

    private final Context mContext;
    private final GoogleApiClient mGoogleApiClient;
    private PendingIntent mPendingIntent;
    private final ActivityRecognitionApi activityRecognitionApi;
    private PendingResult<Status> pendingResult;

    private static MotionTrackerBuilder instance;

    /**
     * Returns the current MotionTrackerBuilder instance in the app
     * @return current MotionTrackerBuilder
     */
    public static MotionTrackerBuilder getInstance(){
        Timber.d("MotionTrackerBuilder.instance='%s'", instance != null ? instance.toString() : null);
        return instance;
    }

    /**
     * Adds connection with GoogleApiClient
     * @param context context of whoever called the receiver
     *
     * @see GoogleApiClient.Builder#addApi(Api)
     */
    public MotionTrackerBuilder(Context context) {
        instance = this;
        mContext = context;
        mPendingIntent = null;
        activityRecognitionApi = ActivityRecognition.ActivityRecognitionApi;

        Timber.d("method=MotionTrackerBuilder action='Creating and connecting to GoogleApiClient.ActivityRecognition API'");
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(ActivityRecognition.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    /**
     * When connected to the GoogleApiClient,
     * requests for activityRecognition changes
     * @param bundle    Bundle of data provided to clients by Google Play services. May be null if no content is provided by the service.
     *
     * @see ActivityRecognitionApi
     */
    @Override
    public void onConnected(Bundle bundle) {
        Timber.d("method=MotionTrackerBuilder.onConnected action='connected to GoogleApiClient.ActivityRecognition API. Requesting activity updates'");
        mPendingIntent = createRequestPendingIntent();

        //MOTION TRACKING
        if (activityRecognitionApi != null) {
            pendingResult = activityRecognitionApi.requestActivityUpdates(mGoogleApiClient, ConfigurationConstants.DETECTION_INTERVAL_ACTIVITY_STOPPING, mPendingIntent);
            pendingResult.setResultCallback(this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.d("method=MotionTrackerBuilder.onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("method=MotionTrackerBuilder.onConnectionFailed");
    }

    @Override
    public void onResult(@NonNull Status status) {
        Timber.d("method=MotionTrackerBuilder.onResult statusMessage='%s'", status.getStatusMessage());
    }

    /**
     * @return intent to call MotionTrackingService
     *
     * @see MotionTrackingService
     */
    private PendingIntent createRequestPendingIntent() {
        if (mPendingIntent == null) {
            Timber.d("method=MotionTrackerBuilder.createRequestPendingIntent");
            Intent intent = new Intent(mContext, MotionTrackingService.class);
            mPendingIntent = PendingIntent.getService(mContext, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return mPendingIntent;
    }

    /**
     * Stops connection with GoogleApiClient so MotionTrackingService won't be called again
     */
    public void stopService() {
        Timber.d("method=MotionTrackerBuilder.stopService action='disconnecting from GoogleApiClient.ActivityRecognition API'");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            activityRecognitionApi.removeActivityUpdates(mGoogleApiClient, mPendingIntent);
            mGoogleApiClient.disconnect();
        }
    }
}
