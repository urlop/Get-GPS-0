package com.example.ruby.mygetgps.models;

import android.location.Location;

import java.util.Calendar;

/**
 * A more complete Location Class. With more information such us the time it was saved.
 */
public class UserLocation {
    private Location location;
    private float[] acceleration;
    private int accelerationAccuracy;
    private Calendar timeSaved;

    public UserLocation() {
    }

    /**
     * Complete constructor
     *
     * @param location              user's location
     * @param acceleration          user's acceleration
     * @param accelerationAccuracy  acceleration's margin of error
     * @param timeSaved             time it was saved
     */
    public UserLocation(Location location, float[] acceleration, int accelerationAccuracy, Calendar timeSaved) {
        this.location = location;
        this.acceleration = acceleration;
        this.accelerationAccuracy = accelerationAccuracy;
        this.timeSaved = timeSaved;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float[] getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float[] acceleration) {
        this.acceleration = acceleration;
    }

    public int getAccelerationAccuracy() {
        return accelerationAccuracy;
    }

    public void setAccelerationAccuracy(int accelerationAccuracy) {
        this.accelerationAccuracy = accelerationAccuracy;
    }

    public Calendar getTimeSaved() {
        return timeSaved;
    }

    public void setTimeSaved(Calendar timeSaved) {
        this.timeSaved = timeSaved;
    }
}
