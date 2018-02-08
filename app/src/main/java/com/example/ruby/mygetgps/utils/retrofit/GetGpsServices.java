package com.example.ruby.mygetgps.utils.retrofit;


import com.example.ruby.mygetgps.models.AuthTokenWS;
import com.example.ruby.mygetgps.models.LocationSave;
import com.example.ruby.mygetgps.models.RecordWS;
import com.example.ruby.mygetgps.models.SignUpUser;
import com.example.ruby.mygetgps.models.TravelerWS;
import com.example.ruby.mygetgps.models.Trip;
import com.example.ruby.mygetgps.models.TripWS;
import com.example.ruby.mygetgps.models.User;
import com.example.ruby.mygetgps.models.network.RecordBody;
import com.example.ruby.mygetgps.models.network.TripBody;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    @FormUrlEncoded
    @POST(Urls.TRIPS)
    Call<TripWS> uploadTrip(@Field("travel[vehicle_type_id]") int vehicle_type_id);

    //:travel_id, :start_latitude, :start_longitude, :end_latitude, :end_longitude, :speed, :time_registered
    @FormUrlEncoded
    @POST(Urls.RECORDS)
    Call<RecordWS> uploadRecord(@Field("record[travel_id]") int travel_id,
                                @Field("record[start_latitude]") float start_latitude,
                                @Field("record[start_longitude]") float start_longitude,
                                @Field("record[end_latitude]") float end_latitude,
                                @Field("record[end_longitude]") float end_longitude,
                                @Field("record[speed]") float speed,
                                @Field("record[time_registered]") Date time_registered);

    @FormUrlEncoded
    @POST(Urls.TRAVELERS)
    Call<TravelerWS> uploadRegistration(@Field("traveler[first_name]") String firstName,
                                        @Field("traveler[last_name]") String lastName,
                                        @Field("traveler[email]") String email,
                                        @Field("traveler[password]") String password,
                                        @Field("traveler[password_confirmation]") String password_confirmation,
                                        @Field("traveler[vehicle_type_id]") int vehicleTypeId);

    @FormUrlEncoded
    @POST(Urls.LOGIN)
    Call<AuthTokenWS> login(@Field("user_login[email]") String email,
                            @Field("user_login[password]") String password);


}
