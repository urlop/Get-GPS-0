package com.example.ruby.mygetgps.services.on_trip;


import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.ruby.mygetgps.GetGpsApplication;
import com.example.ruby.mygetgps.models.DriveState;
import com.example.ruby.mygetgps.models.LocationSave;
import com.example.ruby.mygetgps.models.TripSave;
import com.example.ruby.mygetgps.models.UserLocation;
import com.example.ruby.mygetgps.utils.ConfigurationConstants;
import com.example.ruby.mygetgps.utils.Constants;
import com.example.ruby.mygetgps.utils.TripHelper;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Container for methods related to trip tracking logic
 */
class DriveAlgorithm {

    public static void tripValidation(DriveState driveState, TripSave trip) {
        loggingNewLocation(driveState.getCurrentLocation());
        ArrayList<Location> userLocations = driveState.getLocations();
        validLocation(driveState, driveState.getCurrentUserLocation(), driveState.getPreviousLocation(), trip);
        checkStoppingCondition(userLocations, driveState);
    }

    /**
     * Checks if location is accurate enough, if it it, saves it
     *
     * @param currentLocation  location to be checked. current
     * @param previousLocation last location tracked
     * @param trip             current trip in database
     * @see #saveTripLocation(UserLocation, TripSave)
     */
    private static void validLocation(DriveState driveState, UserLocation currentLocation, Location previousLocation, TripSave trip) {
        boolean vAccuracy = validAccuracy(currentLocation.getLocation());
        boolean vSpeed = validSpeed(currentLocation.getLocation());
        boolean vDeltaDistance = validDeltaDistance(currentLocation.getLocation(), previousLocation);
        boolean vLocation = vAccuracy && vDeltaDistance && vSpeed;

        Timber.d("method=DriveAlgorithm.validLocation location.valid=%b accuracy.valid=%b speed.valid=%b deltaDistance.valid=%b action='saving trip location'",
                vLocation, vAccuracy, vSpeed, vDeltaDistance);

        if (vLocation) {
            //Show speed
            double speed = currentLocation.getLocation().getSpeed();
            if (currentLocation.getLocation().getSpeed() == 0 && trip.getLocations().size() > 1) {
                double distance = TripHelper.distance(currentLocation.getLocation().getLatitude(),
                        currentLocation.getLocation().getLongitude(),
                        previousLocation.getLatitude(),
                        previousLocation.getLongitude()) * 1000; //m
                Log.e("speed - distance", String.valueOf(distance));
                Log.e("speed - time curr", String.valueOf(currentLocation.getLocation().getTime()));
                Log.e("speed - time prev", String.valueOf(previousLocation.getTime()));
                long time = (currentLocation.getLocation().getTime() - previousLocation.getTime()) / 1000; /// 3600 * 3600; //s
                Log.e("speed - time", String.valueOf(time));
                speed = distance / time; //in m/s
                Log.e("speed - speed", String.valueOf(speed));
                if (speed < 0) {
                    speed = 0;
                }
            }

            Toast.makeText(GetGpsApplication.getInstance().getApplicationContext(),
                    "Speed : " + String.valueOf((float)speed),
                    Toast.LENGTH_LONG).show();
            /*Toast.makeText(GetGpsApplication.getInstance(), "Saved: " +
                    "lat: " + driveState.getPreviousLocation().getLatitude() +
                    "lon: " + driveState.getPreviousLocation().getLongitude(),
                    Toast.LENGTH_SHORT).show();*/

            //Save location
            currentLocation.getLocation().setSpeed((float) speed);
            driveState.addToTotalDistance(driveState.getPreviousLocation().distanceTo(driveState.getCurrentLocation()));
            saveTripLocation(currentLocation, trip);
        }
    }

    /**
     * Saves the location into the current trip
     *
     * @param currentLocation location to be saved
     * @param trip            current trip
     */
    private static void saveTripLocation(UserLocation currentLocation, TripSave trip) {
        Log.e("speed saved", String.valueOf(currentLocation.getLocation().getSpeed()));
        LocationSave locationSave = new LocationSave(currentLocation.getLocation().getLatitude(),
                currentLocation.getLocation().getLongitude(),
                currentLocation.getLocation().getSpeed(),
                currentLocation.getAcceleration()[0],
                currentLocation.getAcceleration()[1],
                currentLocation.getAcceleration()[2],
                currentLocation.getLocation().getTime(),
                trip);
        locationSave.save();
    }

    /**
     * Checks if location is accurate enough
     *
     * @param location location to be checked
     * @return true if location is accurate enough
     */
    private static boolean validAccuracy(Location location) {
        return location.getAccuracy() <= ConfigurationConstants.LIMIT_ACCURACY;
    }

    /**
     * Checks if location is fast enough
     *
     * @param location location to be checked
     * @return true if location is fast enough
     */
    private static boolean validSpeed(Location location) {
        return location.getSpeed() >= ConfigurationConstants.MINIMUM_SPEED;
    }

    /**
     * Checks if location shows that user has travelled enough distance
     *
     * @param currentLocation  location to be checked
     * @param previousLocation last location registered
     * @return true if location is fast enough
     */
    private static boolean validDeltaDistance(Location currentLocation, Location previousLocation) {
        return currentLocation.distanceTo(previousLocation) > ConfigurationConstants.MINIMUM_DISTANCE_DELTA;
    }

    /**
     * Checks is user stops driving (stays in the same position for too long / starts to go on foot).
     * And stops the trip if he/she is.
     *
     * @param userLocations locations travelled by user
     * @param driveState    class which contains all information related to the trip.
     */
    private static void checkStoppingCondition(ArrayList<Location> userLocations, DriveState driveState) {

        if (userLocations.size() >= ConfigurationConstants.LAST_N_LOCATIONS) {
            float routeDistance = getRouteDistance(userLocations);

            boolean isUserMoving = isUserMoving(routeDistance);
            boolean isUserDriving = TripHelper.isUserDriving(driveState.getActivityType(), driveState.getActivityConfidence());
            boolean isUserWalking = TripHelper.isUserWalking(driveState.getActivityType(), driveState.getActivityConfidence());

            boolean stoppingCondition = false;
            String stoppingConditionType = "";
            String logMarker = "";

            if (!isUserMoving) {
                stoppingCondition = true;
                stoppingConditionType = Constants.TIMER;
            } else if (Constants.MUST_GET_MOTION_TO_STOP) {
                if (isUserWalking) {
                    stoppingCondition = true;
                    stoppingConditionType = Constants.WALKING_MOTION;
                }
            } else {
                stoppingCondition = false;
                stoppingConditionType = "driving";
            }

            if(stoppingCondition) {
                driveState.setStoppingCondition();
                driveState.setStoppingConditionType(stoppingConditionType);
            }

            if(stoppingCondition){
                logMarker = "TripStopping" + stoppingConditionType;
            }

            Timber.i("method=DriveAlgorithm.checkStoppingCondition stoppingCondition=%s stoppingConditionType='%s' userLocations.size=%d " +
                    "route.distance=%f user.moving=%s user.walking=%s user.driving=%s " + logMarker,
                    stoppingCondition, stoppingConditionType,
                    userLocations.size(), routeDistance, isUserMoving, isUserWalking, isUserDriving
            );
        }
    }

    /**
     * Tells the distance travelled in the ConfigurationConstants#LAST_N_LOCATIONS
     *
     * @param locations all locations travelled
     * @return total distance travelled between first and last point
     */
    private static float getRouteDistance(ArrayList<Location> locations) {
        float distance = 0f;
        int size = locations.size() - 1;
        for (int i = 0; i < ConfigurationConstants.LAST_N_LOCATIONS - 1; i++) {
            distance += locations.get(size - i).distanceTo(locations.get(size - i - 1));
        }
        return distance;
    }

    /**
     * Checks if the travelled distance is greater than ConfigurationConstants#LIMIT_DISTANCE_BETWEEN_N_LOCATIONS
     *
     * @param routeDistance distance travelled in the ConfigurationConstants#LAST_N_LOCATIONS
     * @return true if distance is larger than the minimum
     */
    private static boolean isUserMoving(Float routeDistance) {
        return routeDistance > ConfigurationConstants.LIMIT_DISTANCE_BETWEEN_N_LOCATIONS;
    }

    /**
     * Outputs log with details of the current location
     *
     * @param location current location
     */
    private static void loggingNewLocation(Location location) {
        Timber.d("method=DriveAlgorithm.loggingNewLocation action='new location' provider='%s' latitude=%f longitude=%f altitude=%f bearing=%f speed=%f accuracy=%f",
                location.getProvider(), location.getLatitude(), location.getLongitude(),
                location.getAltitude(), location.getBearing(), location.getSpeed(), location.getAccuracy());
    }
}
