package com.example.ruby.mygetgps.utils.retrofit;


import com.example.ruby.mygetgps.models.SignUpUser;
import com.example.ruby.mygetgps.models.Trip;
import com.example.ruby.mygetgps.models.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Where all web services are going to be accessed
 */
public interface GetGpsServices {

    @POST(Urls.TRIPS)
    Call<Trip> uploadTrip(@Body Trip requestBody);

    @GET(Urls.TRIPS)
    Call<ArrayList<Trip>> getTrips(@Query("uncategorized") boolean uncategorized, @Query("page") int page);

    @PUT(Urls.TRIPS + "/{token_id}")
    Call<Trip> updateTrip(@Path("token_id") String token_id, @Body Trip requestBody);

    @POST(Urls.LOGIN)
    Call<User> loginUser(@Body User loginUser);

    @POST(Urls.SIGN_UP)
    Call<User> signUpUser(@Body SignUpUser signUpUser);

    @GET(Urls.RETRIEVE_CURRENT_USER)
    Call<User> retrieveCurrentUser();

    @DELETE(Urls.TRIPS + "/{token_id}")
    Call<Trip> deleteTrip(@Path("token_id") String token_id);

    @PUT(Urls.RETRIEVE_CURRENT_USER)
    Call<User> updateCurrentUser(@Body HashMap<String, Object> body);

    @PUT(Urls.RETRIEVE_CURRENT_USER)
    Call<User> changeUsersPassword(@Body HashMap<String, HashMap<String, Object>> body);

    @POST(Urls.PASSWORD_RESETS)
    Call<User> passwordReset(@Body HashMap<String, Object> body);





}
