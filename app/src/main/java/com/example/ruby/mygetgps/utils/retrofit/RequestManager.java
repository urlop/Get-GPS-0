package com.example.ruby.mygetgps.utils.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.ruby.mygetgps.BuildConfig;
import com.example.ruby.mygetgps.utils.Constants;
import com.example.ruby.mygetgps.utils.PreferencesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

//import com.facebook.stetho.okhttp3.StethoInterceptor;

/**
 * Retrofit class from where the EverlanceServices are going to be retrieved
 */
public class RequestManager {

    private static GetGpsServices defaultRequestManager;
    private static EverlanceMultipartServices multipartRequestManager;
    private static Retrofit retrofit;
    private static Retrofit retrofitMultipart;

    private RequestManager() {
    }

    /**
     * Obtains EverlanceServices for general purposes.
     * Has the respective header for a user who has already logged in.
     *
     * @return EverlanceServices with authorization header
     */
    public static GetGpsServices getDefault(Context context) {
        if(retrofit == null) {
            retrofit = generateRetrofit(context, "application/json", false);
        }

        if (defaultRequestManager == null) {
            defaultRequestManager = retrofit.create(GetGpsServices.class);
        }
        return defaultRequestManager;
    }

    /**
     * Obtains EverlanceMultipartServices for APIs that expect data sent in multipart/form-data encoding (like file uploads)
     * Has the respective header for a user who has already logged in.
     *
     * @return EverlanceMultipartServices with authorization header
     */
    public static EverlanceMultipartServices getMultipartServices(Context context) {
        if(retrofitMultipart == null) {
            retrofitMultipart = generateRetrofit(context, "multipart/form-data", true);
        }

        if (multipartRequestManager == null) {
            multipartRequestManager = retrofitMultipart.create(EverlanceMultipartServices.class);
        }
        return multipartRequestManager;
    }

    /**
     * Generates a Retrofit API client the corresponding headers, URl, and converter
     *
     * @param contentType content type for request data (application/json or multipart/form-data)
     * @return personalized Retrofit.
     */
    private static Retrofit generateRetrofit(final Context context, final String contentType, boolean addGsonConverter) {
        Gson gson = new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        final OkHttpClient client = getOkHttpClient(context, contentType);
        Timber.d("BASE_API_URL='%s'", Constants.BASE_API_URL);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL);
        if(addGsonConverter) {
            // See https://github.com/square/retrofit/issues/1210
            builder = builder.addConverterFactory(new GsonStringConverterFactory());
        }
        builder = builder.addConverterFactory(GsonConverterFactory.create(gson));
        return builder.client(client).build();
    }

    /**
     * Generates OkHttpClient instance with configured timeouts and auth/logging interceptors
     *
     * @return OkHttpClient
     */
    @NonNull
    private static OkHttpClient getOkHttpClient(final Context context, final String contentType) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(12, TimeUnit.SECONDS)
                .connectTimeout(12, TimeUnit.SECONDS);

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String token = getAuthToken(context);

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "Token token=" + token)
                        .header("Content-Type", contentType)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());
                return chain.proceed(requestBuilder.build());
            }
        });

        if(BuildConfig.DEBUG) {
            //builder.addNetworkInterceptor(new StethoInterceptor());

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(interceptor);
        }

        return builder.build();
    }

    private static String getAuthToken(Context context) {
        String token;
        if (PreferencesManager.getInstance(context).getAuthToken() != null) {
            token = PreferencesManager.getInstance(context).getAuthToken();
        } else {
            //TODO: Eventually stop retrieving default token
            token = "2e376d05171db0e90cbeadeaf493464e";
        }
        return token;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
