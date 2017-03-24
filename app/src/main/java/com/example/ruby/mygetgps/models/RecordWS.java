package com.example.ruby.mygetgps.models;

import java.util.Date;

/**
 * Created by Ruby on 23/03/2017.
 */

public class RecordWS {
    int travel_id;
    float start_latitude;
    float start_longitude;
    float end_latitude;
    float end_longitude;
    float speed;
    Date time_registered;

    public RecordWS() {
    }

    public int getTravel_id() {
        return travel_id;
    }

    public void setTravel_id(int travel_id) {
        this.travel_id = travel_id;
    }

    public float getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(float start_latitude) {
        this.start_latitude = start_latitude;
    }

    public float getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(float start_longitude) {
        this.start_longitude = start_longitude;
    }

    public float getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(float end_latitude) {
        this.end_latitude = end_latitude;
    }

    public float getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(float end_longitude) {
        this.end_longitude = end_longitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Date getTime_registered() {
        return time_registered;
    }

    public void setTime_registered(Date time_registered) {
        this.time_registered = time_registered;
    }
}
