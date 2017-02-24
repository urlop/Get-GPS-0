package com.example.ruby.mygetgps.utils.retrofit;

import com.example.ruby.mygetgps.models.Trip;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Web services that use multipart/form-data content type to send data
 */
public interface EverlanceMultipartServices {

    @Multipart
    @POST(Urls.TRIPS)
    Call<Trip> uploadTripMultiPart(
            @PartMap() Map<String, MultipartBody.Part> mapPhoto,
            @Part("trip[miles]") float miles,
            @Part("trip[from]") String from,
            @Part("trip[to]") String to,
            @Part("trip[notes]") String notes,
            @Part("trip[date]") String startedAt,
            @Part("trip[purpose]") String purpose,
            @Part("trip[income_source]") String businessLine
    );

//    @Multipart
//    @POST(Urls.TRIPS)
//    Call<Trip> uploadTripWithPhoto(@Body Trip requestBody);

    @Multipart
    @POST(Urls.TRIPS)
    Call<Trip> uploadTripMultipart2(
            @Part MultipartBody.Part file,
            @Part("trip[miles]") float miles,
            @Part("trip[from]") String from,
            @Part("trip[to]") String to,
            @Part("trip[notes]") String notes,
            @Part("trip[date]") String startedAt,
            @Part("trip[purpose]") String purpose,
            @Part("trip[income_source]") String businessLine
    );

    @Multipart
    @POST(Urls.TRIPS)
    Call<Trip> uploadTripWithoutPhoto(
            @Part("trip[miles]") float miles,
            @Part("trip[from]") String from,
            @Part("trip[to]") String to,
            @Part("trip[notes]") String notes,
            @Part("trip[date]") String startedAt,
            @Part("trip[purpose]") String purpose,
            @Part("trip[income_source]") String businessLine
    );

    @POST(Urls.TRIPS)
    Call<Trip> rawUploadTrip(@Body RequestBody body);
}
