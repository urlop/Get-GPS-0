package com.example.ruby.mygetgps.models.network;

import com.example.ruby.mygetgps.models.RecordWS;

/**
 * Created by Ruby on 19/04/2017.
 */

public class RecordBody {

    RecordWS trip;

    public RecordBody() {
    }

    public RecordBody(RecordWS trip) {
        this.trip = trip;
    }

    public RecordWS getTrip() {
        return trip;
    }

    public void setTrip(RecordWS trip) {
        this.trip = trip;
    }
}
