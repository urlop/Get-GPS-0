package com.example.ruby.mygetgps.services.on_trip;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.ruby.mygetgps.models.TripSave;
import com.example.ruby.mygetgps.utils.LoggingHelper;
import com.example.ruby.mygetgps.utils.ServiceHelper;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import timber.log.Timber;

/**
 * Service to detect if user is starting a trip. Uses ActivityRecognition for this purpose.
 *
 * @see ActivityRecognitionResult
 */
public class MotionTrackingService extends IntentService {

    public MotionTrackingService() {
        super(MotionTrackingService.class.getName());
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Checks if user is in on foot.
     * If he/she does, notifies TripTrackingService so it will stop.
     * @param intent    value passed to startService(Intent).
     *
     * @see TripTrackingService#uploadValidLocationsService(TripSave)
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            //MOTION TRACKING
            if (ActivityRecognitionResult.hasResult(intent)) {
                getMotionTrackingAndSendIt(intent);
            } else {
                Timber.w("Intent had no data returned");
            }
        }
    }

    /**
     * Sends current user DetectedActivity and its confidence to the TripTrackingService.
     * So TripTrackingService can know if it needs to stop.
     * @param intent    Intent sent to the service. Has important data related with user's activity
     */
    private void getMotionTrackingAndSendIt(Intent intent){
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        DetectedActivity detectedActivity = result.getMostProbableActivity();

        Timber.d("method=MotionTrackingService.getMotionTrackingAndSendIt activity.name=%s activity.confidence=%s action='pass activity from MotionTrackingService to TripTrackingService'",
                LoggingHelper.getActivityString(getApplicationContext(), detectedActivity.getType()), detectedActivity.getConfidence());

        if (isTripTrackingServiceRunning()) {
            ServiceHelper.activityInformationToTripTrackingService(getApplicationContext(), detectedActivity.getType(), detectedActivity.getConfidence());
        } else {
            Timber.w("method=MotionTrackingService.getMotionTrackingAndSendIt action='TripTrackingService is not running. Aborting intent passing.'");
        }
    }

    /**
     * Tells if TripTrackingService is already running. To send it the ActivityDetected data
     * @return  yes if service is running
     */
    private boolean isTripTrackingServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (TripTrackingService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
