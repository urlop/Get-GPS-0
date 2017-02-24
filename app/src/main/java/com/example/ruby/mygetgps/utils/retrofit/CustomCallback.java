package com.example.ruby.mygetgps.utils.retrofit;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ruby.mygetgps.R;
import com.example.ruby.mygetgps.models.APIError;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import timber.log.Timber;


public class CustomCallback<T> implements Callback<T> {

    private final Context context;
    private final Call<T> call;

    public CustomCallback(Context context, Call<T> call) {
        this.context = context;
        this.call = call;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        /*TODO: Check handle {"status":"500","error":"Internal Server Error"}.
        java.lang.NullPointerException: Attempt to invoke virtual method 'java.util.List com.everlance.models.User.getTrips()' on a null object reference
        if (!response.isSuccessful()) { return; }
         */
        if (!response.isSuccessful()) {
            Converter<ResponseBody, APIError> converter = RequestManager.getRetrofit().responseBodyConverter(APIError.class, new Annotation[0]);
            try {
                APIError errors = converter.convert(response.errorBody());
                String error = errors.getResult() != null ? errors.getResult() : errors.getError();
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                Timber.e("method=onResponse response=%s apiResponseError=%s", response.toString(), error);
            } catch (Exception e) {
                Timber.e(e, "error=WS Exception response=%s", response.toString());
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
    }

    private void retry() {
        Timber.d("method=retry action='Trying to retry call'");
        call.clone().enqueue(this);
    }
}
