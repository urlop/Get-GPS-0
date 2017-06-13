package com.example.ruby.mygetgps.services.on_trip;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;

import com.example.ruby.mygetgps.models.DriveState;
import com.example.ruby.mygetgps.models.LocationSave;
import com.example.ruby.mygetgps.models.TripSave;
import com.example.ruby.mygetgps.models.UserLocation;
import com.example.ruby.mygetgps.utils.ServiceHelper;
import com.example.ruby.mygetgps.utils.TripHelper;
import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;
import java.util.Calendar;

import timber.log.Timber;

/**
 * Custom class which contains the listeners that are going to be used by TripTrackingService
 *
 * @see TripTrackingService
 * @see LocationListener
 * @see SensorEventListener
 */
class TripTrackingListener implements LocationListener, SensorEventListener {

    private final TripTrackingService tripTrackingService;
    private final float[] linearAcceleration = new float[3];
    private int linearAccelerationAccuracy = 0;
    private DriveState driveState;
    private static TripSave tripSave;

    /**
     * A new trip is created if there is not a current trip yet.
     * <p/>
     * The accelerometer is declared here in order to be used for testing.
     *
     * @param tripTrackingService service to which it communicates
     */
    public TripTrackingListener(TripTrackingService tripTrackingService) {
        this.tripTrackingService = tripTrackingService;
        driveState = new DriveState();
        tripSave = TripHelper.tripOngoing();
        if (tripSave == null) {
            Timber.i("method=constructor trip=null");
            tripSave = new TripSave();
            tripSave.save();
            Timber.i("method=constructor.save trip=%d", tripSave.getId());
        } else {
            Timber.i("method=constructor trip=%d", tripSave.getId());
            retrieveSavedTripData();
        }

        SensorManager sensorManager = (SensorManager) tripTrackingService.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void retrieveSavedTripData() {
        float distance = 0f;
        Calendar calendar = Calendar.getInstance();
        ArrayList<UserLocation> userLocations = new ArrayList<>();
        for (LocationSave locationSave : tripSave.getLocations()) {
            Location location = new Location("");
            location.setLatitude(locationSave.getLatitude());
            location.setLongitude(locationSave.getLongitude());
            calendar.setTimeInMillis(locationSave.getTime());
            userLocations.add(new UserLocation(location, linearAcceleration, linearAccelerationAccuracy, calendar));
        }
        for (int i = 0; i < userLocations.size() - 1; i++) {
            distance += userLocations.get(i).getLocation().distanceTo(userLocations.get(i + 1).getLocation());
        }
        driveState.setUserLocations(userLocations);
        driveState.addToTotalDistance(distance);
    }

    /**
     * Starts when location changes.
     * Here new locations are saved locally and externally.
     * If it is detected has stopped, that data is saved in the database and the service stops.
     *
     * @param location new location detected
     */
    @Override
    public void onLocationChanged(Location location) {
        driveState.addLocation(new UserLocation(location, linearAcceleration, linearAccelerationAccuracy, Calendar.getInstance()));
        DriveAlgorithm.tripValidation(driveState, tripSave);
        tripTrackingService.broadcastLocation(location, driveState);
        Timber.d("method=TripTrackingListener.onLocationChanged driveState.isStoppingCondition=%b", driveState.isStoppingCondition());
        if (driveState.isStoppingCondition()) {
            Timber.d("method=TripTrackingListener.onLocationChanged marker=StoppingConditionMet driveState.stoppingConditionType=%s", driveState.getStoppingConditionType());
            ServiceHelper.stopTripTrackingService(tripTrackingService, driveState.getStoppingConditionType());
        }
    }

    /**
     * Starts when a change in the sensors is detected.
     * It saves the new acceleration to use it later for testing
     *
     * @param event new event which contains all declared sensors information
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        linearAcceleration[0] = event.values[0];
        linearAcceleration[1] = event.values[1];
        linearAcceleration[2] = event.values[2];
    }

    /**
     * Starts when a change in the sensors' accuracy is detected.
     * It saves the new accuracy to use it later for testing.
     *
     * @param sensor   sensor which accuracy has changed
     * @param accuracy new accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        linearAccelerationAccuracy = accuracy;
    }

    /**
     * Sets activity type into current DriveState
     *
     * @param activityType       type of activity detected
     * @param activityConfidence margin or error for confidence
     */
    public void setDriveStateDetectedActivity(int activityType, int activityConfidence) {
        driveState.setActivityType(activityType);
        driveState.setActivityConfidence(activityConfidence);
    }

    /**
     * Sets value of column start_method of TRIP_SAVE table
     *
     * @param startMethod
     */
    public void setStartMethod(String startMethod) {
        tripSave.setStartMethod(startMethod);
        tripSave.save();
    }

    /**
     * Sets value of column stop_method of TRIP_SAVE table
     *
     * @param stopMethod
     */
    public void setStopMethod(String stopMethod) {
        tripSave.setStopMethod(stopMethod);
        tripSave.save();
    }

    /**
     * @return current trip in database
     */
    public TripSave getTripSave() {
        Timber.d("method=getTripSave id=%d", tripSave.getId());
        return tripSave;
    }

    /**
     * @return current driveState class with all information related with trip;
     */
    public DriveState getDriveState() {
        return driveState;
    }

    public void resetDriveState(){
        driveState = new DriveState();
        tripSave = null;
    }
}
