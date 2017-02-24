package com.example.ruby.mygetgps.services.on_trip;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.example.ruby.mygetgps.R;
import com.example.ruby.mygetgps.models.DriveState;
import com.example.ruby.mygetgps.models.TripSave;
import com.example.ruby.mygetgps.ui.activities.MainActivity;
import com.example.ruby.mygetgps.utils.ConfigurationConstants;
import com.example.ruby.mygetgps.utils.Constants;
import com.example.ruby.mygetgps.utils.LoggingHelper;
import com.example.ruby.mygetgps.utils.PermissionUtil;
import com.example.ruby.mygetgps.utils.PreferencesManager;
import com.example.ruby.mygetgps.utils.ServiceHelper;
import com.example.ruby.mygetgps.utils.TestHelper;
import com.example.ruby.mygetgps.utils.TripHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import timber.log.Timber;

/**
 * Service for tracking trip when it has already started.
 */
public class TripTrackingService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final int TURN_ON_GPS_NOTIFICATION_ID = 103;

    public static final String ACTION = "com.everlance.TripTrackingService.LocationBroadcast";
    public static final String ACTION_STOPPED = "com.everlance.TripTrackingService.Stopped";

    private GoogleApiClient mGoogleApiClient;
    private TripTrackingListener tripTrackingListener;

    /**
     * Connects a new GoogleApiClient.
     * Sends a notification to user asking him/her to turn on his/her GPS
     *
     * @see GoogleApiClient
     */
    @Override
    public void onCreate() {
        Timber.i("method=TripTrackingService.onCreate action='Creating and connecting to GoogleApiClient.LocationServices API'");
        mGoogleApiClient = new GoogleApiClient.Builder(TripTrackingService.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(TripTrackingService.this)
                .addOnConnectionFailedListener(TripTrackingService.this).build();

        mGoogleApiClient.connect();
        tripTrackingListener = new TripTrackingListener(this);
    }

    /**
     * Sends DetectedActivity's data to it's TripTrackingListener
     *
     * @param intent  with extra information to be used in this method. The Intent supplied to startService(Intent), as given. This may be null if the service is being restarted after its process has gone away, and it had previously returned anything except START_STICKY_COMPATIBILITY.
     * @param flags   Additional data about this start request. Currently either 0, START_FLAG_REDELIVERY, or START_FLAG_RETRY.
     * @param startId A unique integer representing this specific request to start. Use with stopSelfResult(int).
     * @return value which indicates what semantics the system should use for the service's current started state. It may be one of the constants associated with the START_CONTINUATION_MASK bits.
     * @see TripTrackingListener
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            if (intent.hasExtra(ConfigurationConstants.START_METHOD_EXTRA)) {
                Timber.i("method=TripTrackingService.onStartCommand intent.extras.startMethod='%s'", intent.getExtras().getString(ConfigurationConstants.START_METHOD_EXTRA));
                tripTrackingListener.setStartMethod(intent.getExtras().getString(ConfigurationConstants.START_METHOD_EXTRA));
            } else if (intent.hasExtra(ConfigurationConstants.STOP_METHOD_EXTRA)) {
                Timber.i("method=TripTrackingService.onStartCommand intent.extras.stopMethod='%s'", intent.getExtras().getString(ConfigurationConstants.STOP_METHOD_EXTRA));
                tripTrackingListener.setStopMethod(intent.getExtras().getString(ConfigurationConstants.STOP_METHOD_EXTRA));
                stopSelf();
            } else {
                int activityType = intent.getExtras().getInt(Constants.ACTIVITY_TYPE_EXTRA);
                int activityConfidence = intent.getExtras().getInt(Constants.ACTIVITY_CONFIDENCE_EXTRA);
                Timber.d("method=TripTrackingService.onStartCommand action='intent.extras.receivedActivity' activity.name='%s' activity.confidence=%d",
                        LoggingHelper.getActivityString(getApplicationContext(), activityType), activityConfidence);
                tripTrackingListener.setDriveStateDetectedActivity(activityType, activityConfidence);
            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * Includes a process to stop related features, and save the necessary data.
     *
     * @see #saveAndStop()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        broadcastStopTripTrackingService();
        saveAndStop();
    }

    /**
     * Starts when the connect request has successfully completed.
     * After this mTouchHelper, the application can make requests on other methods provided by the
     * client and expect that no user intervention is required to call methods that use account
     * and scopes provided to the client constructor.
     *
     * @param bundle Bundle of data provided to clients by Google Play services. May be null if no content is provided by the service.
     * @see #startLocationUpdates()
     */
    @Override
    public void onConnected(Bundle bundle) {
        // Begin polling for new location updates.
        startLocationUpdates();
        LocationManager manager = (LocationManager) getSystemService(getApplication().LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            sendNotificationNoGps(getString(R.string.notification_trip_started_no_gps_title), getString(R.string.notification_trip_started_no_gps_message), TURN_ON_GPS_NOTIFICATION_ID);
        }

    }

    /**
     * Asks for location updates to the GoogleApiClient
     *
     * @see GoogleApiClient
     */
    private void startLocationUpdates() {
        // Create the location request
        Timber.i("method=TripTrackingService.startLocationUpdates " +
                        "action='connected to GoogleApiClient.FusedLocationApi API. Requesting activity updates' " +
                        "priority=PRIORITY_HIGH_ACCURACY interval=%d fastestInterval=%d smallestDisplacement=%f",
                ConfigurationConstants.UPDATE_INTERVAL, ConfigurationConstants.FASTEST_INTERVAL, ConfigurationConstants.SMALLEST_DISPLACEMENT);
        LocationRequest mLocationRequest;
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(ConfigurationConstants.UPDATE_INTERVAL)
                .setFastestInterval(ConfigurationConstants.FASTEST_INTERVAL)
                .setSmallestDisplacement(ConfigurationConstants.SMALLEST_DISPLACEMENT);

        // Request location updates
        if (PermissionUtil.checkLocationPermission(this)) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, tripTrackingListener);
        }

    }

    /**
     * Stops asking for location updates to the GoogleApiClient
     *
     * @see GoogleApiClient
     */
    private void stopLocationUpdates() {
        Timber.d("method=TripTrackingService.stopLocationUpdates action='disconnecting from GoogleApiClient.FusedLocationApi API'");
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, tripTrackingListener);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.e("Connection to GoogleApiClient.FusedLocationApi client failed");
    }

    /**
     * Stops asking for location updates or broadcast calls.
     * Sends data to the UploadService.
     * Generates testing files
     *
     * @see #stopLocationUpdates()
     * @see #uploadValidLocationsService(TripSave)
     * @see #saveForTesting(DriveState, TripSave)
     */
    private void saveAndStop() {
        stopLocationUpdates();
        Timber.d("method=TripTrackingService.saveAndStop marker=StoppingTrip action='stopping trip and saving data'");
        //TripTabFragment.getInstance().setProgressBarVisibility(true);
        uploadValidLocationsService(tripTrackingListener.getTripSave());
        //FOR TESTING
        saveForTesting(tripTrackingListener.getDriveState(), tripTrackingListener.getTripSave());
        if (tripTrackingListener.getTripSave().getStopMethod() == null) {
            tripTrackingListener.setStopMethod(Constants.APP_WILL_TERMINATE);
        }

        tripTrackingListener.resetDriveState();
    }

    /**
     * Starts and sends data to UploadService
     *
     * @param tripSave trip in database to be uploaded
     */
    private void uploadValidLocationsService(TripSave tripSave) {
        Timber.d("method=TripTrackingService.uploadValidLocationsService");
        float tripDistance = tripTrackingListener.getDriveState().getTotalDistance();
        tripSave.setMiles(tripDistance);
        tripSave.save();

        sendNotificationNoGps("Trip ended", "distance: " + tripDistance, 105);

        if (TripHelper.validateFinalTrip(tripSave)) {
            Timber.d("method=uploadValidLocationsService marker=UploadingValidTrip trip.valid=true trip.distance=%f", tripDistance);
            ServiceHelper.uploadTripService(this, tripSave);
        } else {
            Timber.d("method=uploadValidLocationsService marker=TripTooShortForUpload trip.valid=false trip.distance=%f", tripDistance);
            tripSave.deleteLocations();
            tripSave.delete();
            //TripTabFragment.getInstance().setProgressBarVisibility(false);
            Toast.makeText(getApplicationContext(), R.string.trip_error_invalid, Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Sends trip information to the broadcast to change view
     *
     * @param location   current location
     * @param driveState class with all information related to trip
     */
    void broadcastLocation(Location location, DriveState driveState) {
        Intent in = new Intent(ACTION);
        in.putExtra(Constants.LATITUDE_EXTRA, location.getLatitude());
        in.putExtra(Constants.LONGITUDE_EXTRA, location.getLongitude());
        in.putExtra(Constants.DISTANCE_EXTRA, driveState.getTotalDistance());
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);
    }

    /**
     * Runs the broadcast to change view
     */
    private void broadcastStopTripTrackingService() {
        PreferencesManager.getInstance(this).saveTripStartedState(false);
        Timber.d("method=TripTrackingService.broadcastStopTripTrackingService tripStartedState='%s'", PreferencesManager.getInstance(this).getTripStartedState());
        Intent in = new Intent(ACTION_STOPPED);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);
    }

    /**
     * Generates and saves testing files
     *
     * @param driveState class with all information related to trip. Saved locally
     * @param tripSave   trip saved in database
     */
    private void saveForTesting(DriveState driveState, TripSave tripSave) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);

        StringBuilder sbAll = TestHelper.getAllLocations(driveState);
        StringBuilder sbSaved = TestHelper.getSavedLocations(tripSave);

        String filenameAll = "EvAll" + (dateFormat.format(Calendar.getInstance().getTimeInMillis())) + ".txt";
        String filenameSaved = "EvSaved" + (dateFormat.format(Calendar.getInstance().getTimeInMillis())) + ".txt";

        String allInfo = String.valueOf(sbAll);
        String savedInfo = String.valueOf(sbSaved);

        File filedir = Environment.getExternalStorageDirectory();

        File fileAll = new File(filedir, filenameAll);
        File fileSaved = new File(filedir, filenameSaved);
        try {
            FileOutputStream outputStream;

            if (fileAll.createNewFile()) {
                outputStream = new FileOutputStream(fileAll);
                outputStream.write(allInfo.getBytes());
                outputStream.close();
            }
            if (fileSaved.createNewFile()) {
                outputStream = new FileOutputStream(fileSaved);
                outputStream.write(savedInfo.getBytes());
                outputStream.close();
            }
        } catch (Exception e) {
            Timber.e(e, "method=saveForTesting error=Unable to save trip logs");
        }
    }

    /**
     * Sends a notification telling the user to turn on his/her GPS if it is off
     *
     * @param notificationTitle title of notification
     * @param notificationText  message in the notification
     * @param id                identifier of the notification
     */
    private void sendNotificationNoGps(String notificationTitle, String notificationText, int id) {
        PowerManager pm = (PowerManager) getApplicationContext()
                .getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                getApplicationContext()).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL).setAutoCancel(false);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notificationBuilder.build());

        wakeLock.release();
    }

}

