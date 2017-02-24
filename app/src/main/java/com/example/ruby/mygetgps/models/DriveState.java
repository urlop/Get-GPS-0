package com.example.ruby.mygetgps.models;

import android.location.Location;

import java.util.ArrayList;

/**
 * Class which contains all the information related to the trip. It is saved locally.
 */
public class DriveState {

    private float totalDistance;
    private int activityType;
    private int activityConfidence;
    private boolean stoppingCondition = false;
    private String stoppingConditionType = "";
    private ArrayList<UserLocation> userLocations = new ArrayList<>();

    public DriveState() {
    }

    public void addLocation(UserLocation location) {
        userLocations.add(location);
    }

    /**
     * @return UserLocation class which contains more information related to the Location.
     * @see UserLocation
     */
    public UserLocation getCurrentUserLocation() {
        return userLocations.get(userLocations.size() - 1);
    }

    /**
     * @return just the current Location
     */
    public Location getCurrentLocation() {
        return userLocations.get(userLocations.size() - 1).getLocation();
    }

    /**
     * @return just the second-to-last Location
     */
    public Location getPreviousLocation() {
        if (userLocations.size() > 2) {
            return userLocations.get(userLocations.size() - 2).getLocation();
        }
        return getCurrentLocation();
    }

    public void addToTotalDistance(float distance) {
        totalDistance += distance;
    }

    /**
     * @return all UserLocations.
     * @see UserLocation
     */
    public ArrayList<UserLocation> getUserLocations() {
        return userLocations;
    }

    /**
     * @return just all Locations saved inside DriveState
     */
    public ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        for (UserLocation ul : userLocations) {
            locations.add(ul.getLocation());
        }
        return locations;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public int getActivityConfidence() {
        return activityConfidence;
    }

    public void setActivityConfidence(int activityConfidence) {
        this.activityConfidence = activityConfidence;
    }

    public boolean isStoppingCondition() {
        return stoppingCondition;
    }

    public void setStoppingCondition() {
        this.stoppingCondition = true;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setUserLocations(ArrayList<UserLocation> userLocations) {
        this.userLocations = userLocations;
    }

    public String getStoppingConditionType() {
        return stoppingConditionType;
    }

    public void setStoppingConditionType(String stoppingConditionType) {
        this.stoppingConditionType = stoppingConditionType;
    }
}
