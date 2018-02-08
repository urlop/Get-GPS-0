package com.example.ruby.mygetgps.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.ruby.mygetgps.R;

/**
 * Class for managing everything related to SharedPreferences
 */
public class PreferencesManager {

    private static PreferencesManager self;
    private final SharedPreferences mPreferences;
    private static final String PREFERENCES_NAME = "GetGPS";
    private static final String AUTOMATIC_TRACKING_SWITCH_STATE = "SwitchState";
    private static final String AUTH_TOKEN = "AuthToken";
    private static final String TRIP_STARTED_STATE = "tripStartedState";
    private static final String AUTO_CLASSIFY_STATE = "AutoClassifyState";
    private static final String VEHICLE_TYPE = "VehicleType";
    private static final String TOKEN = "Token";

    /**
     * Sets SharedPreferences' value
     *
     * @param context Environment data where manager was called
     * @see Context#getSharedPreferences(String, int)
     */
    private PreferencesManager(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Gets instance of the manager for better performance
     *
     * @param context Environment data where manager was called
     * @return instance of shared preferences manager
     */
    public static PreferencesManager getInstance(Context context) {
        if (self == null) {
            self = new PreferencesManager(context);
        }
        return self;
    }

    /**
     * Changes AUTOMATIC_TRACKING_SWITCH_STATE value in SharedPreferences
     *
     * @param state state of switch for automatic tracking. If true, automatic trip is on; if not, false
     */
    public void saveAutomaticTrackingSwitchState(Boolean state) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(AUTOMATIC_TRACKING_SWITCH_STATE, state);
        editor.apply();
    }

    /**
     * Retrieves AUTOMATIC_TRACKING_SWITCH_STATE value from SharedPreferences
     *
     * @return if automatic tracking is on
     */
    public Boolean getAutomatictTrackingSwitchState() {
        return mPreferences.getBoolean(AUTOMATIC_TRACKING_SWITCH_STATE, false);
    }

    /**
     * Changes THE AUTH TOKEN value in SharedPreferences
     *
     * @param authToken AUTHORIZATON token used for making requests
     */
    public void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(AUTH_TOKEN, authToken);
        editor.apply();
    }

    /**
     * Retrieves AUTH TOKEN value from SharedPreferences
     *
     * @return the authorization token
     */
    public String getAuthToken() {
        return mPreferences.getString(AUTH_TOKEN, "");
    }

    public void saveTripStartedState(boolean state) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(TRIP_STARTED_STATE, state);
        editor.apply();
    }

    public Boolean getTripStartedState() {
        return mPreferences.getBoolean(TRIP_STARTED_STATE, false);
    }

    public void saveAutoClassifyState(String autoClassify) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(AUTO_CLASSIFY_STATE, autoClassify);
        editor.apply();
    }

    public String getAutoClassifyState() {
        //empty string means Autoclassification is Off
        return mPreferences.getString(AUTO_CLASSIFY_STATE, "");
    }

    public boolean autoClassifyStateIsOff(Context context) {
        return getAutoClassifyState().isEmpty() || getAutoClassifyState().equalsIgnoreCase(context.getString(R.string.str_off));
    }

    public void deleteAllSharedPreferences() {
        mPreferences.edit().clear().commit();
    }

    public void saveVehicleType(int vehicleType) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(VEHICLE_TYPE, vehicleType);
        editor.apply();
    }

    public int getVehicleType() {
        return mPreferences.getInt(VEHICLE_TYPE, 1);
    }

}
