package com.example.ruby.mygetgps.models.network;

import com.example.ruby.mygetgps.models.TripSave;
import com.example.ruby.mygetgps.models.TripWS;
import com.google.gson.JsonObject;

/**
 * Created by Ruby on 19/04/2017.
 */

public class TripBody {
    TripWS trip;

    public TripBody() {
    }

    public TripBody(TripWS trip) {
        this.trip = trip;
    }

    public TripWS getTrip() {
        return trip;
    }

    public void setTrip(TripWS trip) {
        this.trip = trip;
    }
}
