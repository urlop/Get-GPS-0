package com.example.ruby.mygetgps.utils;

import android.content.Context;
import android.content.res.Resources;

import com.example.ruby.mygetgps.R;
import com.google.android.gms.location.DetectedActivity;

/**
 * Contains methods related with converting int values to strings
 */
public class LoggingHelper { //TODO: Rename

    /**
     * Converts a DetectedActivity integer value into a value understandable by humans.
     *
     * @param context              Environment data where method was called
     * @param detectedActivityType integer of activity detected
     * @return String of detected activity
     */
    public static String getActivityString(Context context, int detectedActivityType) {
        Resources resources = context.getResources();
        switch (detectedActivityType) {
            case DetectedActivity.IN_VEHICLE:
                return resources.getString(R.string.in_vehicle);
            case DetectedActivity.ON_BICYCLE:
                return resources.getString(R.string.on_bicycle);
            case DetectedActivity.ON_FOOT:
                return resources.getString(R.string.on_foot);
            case DetectedActivity.RUNNING:
                return resources.getString(R.string.running);
            case DetectedActivity.STILL:
                return resources.getString(R.string.still);
            case DetectedActivity.TILTING:
                return resources.getString(R.string.tilting);
            case DetectedActivity.UNKNOWN:
                return resources.getString(R.string.unknown);
            case DetectedActivity.WALKING:
                return resources.getString(R.string.walking);
            default:
                return resources.getString(R.string.unidentifiable_activity, detectedActivityType);
        }
    }


}
