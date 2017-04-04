package com.example.ruby.mygetgps.utils.retrofit;


import com.example.ruby.mygetgps.models.LocationSave;
import com.example.ruby.mygetgps.models.RecordWS;
import com.example.ruby.mygetgps.models.SignUpUser;
import com.example.ruby.mygetgps.models.Trip;
import com.example.ruby.mygetgps.models.TripWS;
import com.example.ruby.mygetgps.models.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Where all web services are going to be accessed
 */
public interface GetGpsServices {

    //:vehicle_type_id
    @POST(Urls.TRIPS)
    Call<TripWS> uploadTrip(@Field("trip") TripWS requestBody);

    //:travel_id, :start_latitude, :start_longitude, :end_latitude, :end_longitude, :speed, :time_registered
    @POST(Urls.RECORDS)
    Call<RecordWS> uploadRecord(@Field("record") RecordWS requestBody);

}
