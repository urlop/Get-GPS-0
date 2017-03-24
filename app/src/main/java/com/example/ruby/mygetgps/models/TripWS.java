package com.example.ruby.mygetgps.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Ruby on 23/03/2017.
 */

public class TripWS {
    @Expose
    int id;

    int vehicle_type_id;

    public TripWS() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(int vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }
}
