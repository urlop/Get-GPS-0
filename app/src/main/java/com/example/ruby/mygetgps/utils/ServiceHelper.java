package com.example.ruby.mygetgps.utils;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ruby.mygetgps.models.Trip;
import com.example.ruby.mygetgps.models.TripSave;
import com.example.ruby.mygetgps.services.on_trip.MotionTrackerBuilder;
import com.example.ruby.mygetgps.services.on_trip.TripTrackingService;
import com.example.ruby.mygetgps.services.prev_trip.DetectTripStartedBuilder;

import timber.log.Timber;

/**
 * Helper class with methods for managing app services
 */
public class ServiceHelper {

    /**
     * Starts TripTrackingService (default).
     * Needs to stop StartTripService.
     * And creates the MotionTrackerBuilder which will give TripTrackingService user's DetectedActivity.
     *
     * @param context Environment data where method was called
     * @see TripTrackingService
     */
    public static void startTripTrackingService(Context context, String startMethod) {
        Timber.i("method=startTripTrackingService marker=StartingTripTrackingService startMethod=%s", startMethod);

        Timber.d("method=startTripTrackingService preferences.set.TRIP_STARTED_STATE=true");
        PreferencesManager.getInstance(context).saveTripStartedState(true);
        stopStartTripService();

        // TODO: should the reference to this object be kept beyond the MotionTrackerBuilder.class?
        Timber.d("method=startTripTrackingService action='instantiate MotionTrackerBuilder object'");
        new MotionTrackerBuilder(context);

        Intent tripTrackingService = new Intent(context, TripTrackingService.class);
        tripTrackingService.putExtra(ConfigurationConstants.START_TRIP_EXTRA, true);
        tripTrackingService.putExtra(ConfigurationConstants.START_METHOD_EXTRA, startMethod);
        context.startService(tripTrackingService);
    }

    /**
     * Stops TripTrackingService and the MotionTrackerServices
     *
     * @param context Environment data where method was called
     * @see #restartIfAutomaticOn(Context)
     * @see MotionTrackerBuilder
     * @see com.example.ruby.mygetgps.services.on_trip.MotionTrackingService
     */
    public static void stopTripTrackingService(Context context, String stopMethod) {
        Timber.i("method=stopTripTrackingService marker=StoppingTripTrackingService stopMethod=%s", stopMethod);
        if (MotionTrackerBuilder.getInstance() != null) {
            Timber.d("method=startTripTrackingService action='instantiate MotionTrackerBuilder object'");
            MotionTrackerBuilder.getInstance().stopService();
        }
        PreferencesManager.getInstance(context).saveTripStartedState(false);
        Intent tripTrackingService = new Intent(context, TripTrackingService.class);
        if (!stopMethod.equals(Constants.APP_WILL_TERMINATE)) {
            tripTrackingService.putExtra(ConfigurationConstants.STOP_METHOD_EXTRA, stopMethod);
            context.startService(tripTrackingService);
            restartIfAutomaticOn(context);
        } else {
            context.stopService(tripTrackingService);
        }
    }

    /**
     * Sends data to TripTrackingService related to DetectedActivity
     *
     * @param context            Environment data where method was called
     * @param activityType       type of activity detected
     * @param activityConfidence margin of error for detected activity
     */
    public static void activityInformationToTripTrackingService(Context context, int activityType, int activityConfidence) {
        Timber.i("method=activityInformationToTripTrackingService activity.name='%s' activity.confidence=%d", LoggingHelper.getActivityString(context, activityType), activityConfidence);
        Intent intent = new Intent(context, TripTrackingService.class);
        intent.putExtra(Constants.ACTIVITY_TYPE_EXTRA, activityType);
        intent.putExtra(Constants.ACTIVITY_CONFIDENCE_EXTRA, activityConfidence);
        context.startService(intent);
    }

    /**
     * Starts and sends data to UploadService
     *
     * @param context  where method was called
     * @param tripSave trip in database to be uploaded
     */
    public static void uploadTripService(Context context, TripSave tripSave) {
        Timber.d("method=uploadTripService marker=StartingTripUpload trip=%d", tripSave.getId());
        //TODO Upload trip AND THEN delete
        /*Intent uploadServiceIntent = new Intent(context, UploadService.class);
        uploadServiceIntent.putExtra(Constants.TRIP_ID_EXTRA, tripSave.getId());
        context.startService(uploadServiceIntent);*/
        tripSave.deleteLocations();
        tripSave.delete();
    }

    /**
     * Starts UploadService
     *
     * @param context where method was called
     */
    public static void uploadService(Context context) {
        Timber.i("method=uploadService marker=StartingTripUploadService");
        /*Intent uploadServiceIntent = new Intent(context, UploadService.class);
        context.startService(uploadServiceIntent);*/
    }

    /**
     * Starts StartTripService if not created yet
     *
     * @param context  context where method was invoked
     * @param location last location registered
     */
    public static void startStartTripService(Context context, Location location) {
        String message = "method=startStartTripService marker=StartingTripDetectionService ";
        if (location != null) {
            Timber.i(message + "lastLocation.latitude=%f lastLocation.longitude=%s", location.getLatitude(), location.getLongitude());
        } else {
            Timber.w(message + "lastLocation=null");
        }
        new DetectTripStartedBuilder(context, location);
    }

    /**
     * Stops StartTripService if necessary
     */
    public static void stopStartTripService() {
        Timber.i("marker=StoppingTripDetectionService");
        if (DetectTripStartedBuilder.getInstance() != null) {
            DetectTripStartedBuilder.getInstance().stopService();
        }
    }

    /**
     * Starts StartTripService if users configuration says so.
     *
     * @param context Context where method was called from
     */

    private static void restartIfAutomaticOn(Context context) {
        Timber.i("method=restartIfAutomaticOn marker=RestartingTripDetectionServiceForAutomaticDetection");
        //TODO Check if add restart to preferences
        //if (PreferencesManager.getInstance(context).getAutomatictTrackingSwitchState()) {
        startStartTripService(context, null);
        //}
    }

    /**
     * Tells broadcast that trip has stopped
     */
    public static void broadCastTripStopped(Context context, Trip trip) {
        /*Intent in = new Intent(UploadService.ACTION);
        in.putExtra(Constants.TRIP_OBJECT_EXTRA, trip);
        LocalBroadcastManager.getInstance(context).sendBroadcast(in);*/
    }
}
