package com.example.ruby.mygetgps.models;

import com.orm.SugarRecord;

/**
 * Location Model for data saved in the database. With extra data for testing purposes.
 */
public class LocationSave extends SugarRecord {
    private double latitude;
    private double longitude;
    private long time;

    //FOR TEST
    private float speed;
    private float accelerationX;
    private float accelerationY;
    private float accelerationZ;

    private TripSave trip;

    public LocationSave() {

    }

    /**
     * Complete constructor
     *
     * @param latitude      location's latitude
     * @param longitude     location's longitude
     * @param speed         speed of the user at that moment. For testing purposes.
     * @param accelerationX user's acceleration in axis X. For testing purposes.
     * @param accelerationY user's acceleration in axis Y. For testing purposes.
     * @param accelerationZ user's acceleration in axis Z. For testing purposes.
     */
    public LocationSave(double latitude, double longitude, float speed, float accelerationX, float accelerationY, float accelerationZ, long time, TripSave trip) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
        this.accelerationZ = accelerationZ;
        this.trip = trip;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public TripSave getTrip() {
        return trip;
    }

    public void setTrip(TripSave trip) {
        this.trip = trip;
    }

    public long getTime() {
        return time;
    }
}
