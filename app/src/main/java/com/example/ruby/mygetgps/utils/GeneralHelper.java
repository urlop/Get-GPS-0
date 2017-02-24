package com.example.ruby.mygetgps.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;

import com.example.ruby.mygetgps.R;
import com.example.ruby.mygetgps.models.Trip;
import com.example.ruby.mygetgps.models.User;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class GeneralHelper {

    private static final DecimalFormat oneDigit = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));//format to 1 decimal place

    public static void addingMilesRecentlyPostedTrip(Context context, User user, Trip trip) {
        Resources resources = context.getResources();
        if (user!=null && trip!=null) {
            if (resources.getString(R.string.str_work).equalsIgnoreCase(trip.getPurpose())) {
                float workMiles = Float.parseFloat(user.getTotalBusinessMiles()) + trip.getMiles();
                user.setTotalBusinessMiles(String.valueOf(oneDigit.format(workMiles)));
            } else if (resources.getString(R.string.str_personal).equalsIgnoreCase(trip.getPurpose())) {
                float personalMiles = Float.parseFloat(user.getTotalPersonalMiles()) + trip.getMiles();
                user.setTotalPersonalMiles(String.valueOf(oneDigit.format(personalMiles)));
            } else {
                float uncategorizedMiles = Float.parseFloat(user.getTotalUncategorizedMiles()) + trip.getMiles();
                user.setTotalUncategorizedMiles(String.valueOf(oneDigit.format(uncategorizedMiles)));
            }
        }
    }

    public static void substractingMilesUnclassifiedTrips(User user, Trip trip) {
        float uncategorizedMiles = Float.parseFloat(user.getTotalUncategorizedMiles()) - trip.getMiles();
        user.setTotalUncategorizedMiles(String.valueOf(oneDigit.format(uncategorizedMiles)));
    }

    public static void onSwipeUpdateUserMiles(Context context, Trip trip, User user) {
        Resources resources = context.getResources();
        if (resources.getString(R.string.str_work).equalsIgnoreCase(trip.getPurpose())) {
            float workMiles = Float.parseFloat(user.getTotalBusinessMiles()) + trip.getMiles();
            user.setTotalBusinessMiles(String.valueOf(oneDigit.format(workMiles)));
        } else if (resources.getString(R.string.str_personal).equalsIgnoreCase(trip.getPurpose())) {
            float personalMiles = Float.parseFloat(user.getTotalPersonalMiles()) + trip.getMiles();
            user.setTotalPersonalMiles(String.valueOf(oneDigit.format(personalMiles)));
        } else if (resources.getString(R.string.str_medical).equalsIgnoreCase(trip.getPurpose())) {
            float medicalMiles = Float.parseFloat(user.getTotalMedicalMiles()) + trip.getMiles();
            user.setTotalMedicalMiles(String.valueOf(oneDigit.format(medicalMiles)));
        } else {
            float charityMiles = Float.parseFloat(user.getTotalCharityMiles()) + trip.getMiles();
            user.setTotalCharityMiles(String.valueOf(oneDigit.format(charityMiles)));
        }
        user.setTotalUncategorizedMiles(String.valueOf(oneDigit.format(Float.parseFloat(user.getTotalUncategorizedMiles()) - trip.getMiles())));
    }

    public static void onSwipeAllUpdateUserMiles(Context context, Trip trip, User user, String prevPurpose) {
        Resources resources = context.getResources();
        //Add miles to new Purpose
        if (resources.getString(R.string.str_work).equalsIgnoreCase(trip.getPurpose())) {
            float workMiles = Float.parseFloat(user.getTotalBusinessMiles()) + trip.getMiles();
            user.setTotalBusinessMiles(String.valueOf(oneDigit.format(workMiles)));
        } else if (resources.getString(R.string.str_personal).equalsIgnoreCase(trip.getPurpose())) {
            float personalMiles = Float.parseFloat(user.getTotalPersonalMiles()) + trip.getMiles();
            user.setTotalPersonalMiles(String.valueOf(oneDigit.format(personalMiles)));
        } else if (resources.getString(R.string.str_medical).equalsIgnoreCase(trip.getPurpose())) {
            float medicalMiles = Float.parseFloat(user.getTotalMedicalMiles()) + trip.getMiles();
            user.setTotalMedicalMiles(String.valueOf(oneDigit.format(medicalMiles)));
        } else if (resources.getString(R.string.str_charity).equalsIgnoreCase(trip.getPurpose())) {
            float charityMiles = Float.parseFloat(user.getTotalCharityMiles()) + trip.getMiles();
            user.setTotalCharityMiles(String.valueOf(oneDigit.format(charityMiles)));
        }

        //Remove miles from previous Purpose
        if (prevPurpose == null) {
            float uncategorizedMiles = Float.parseFloat(user.getTotalUncategorizedMiles()) - trip.getMiles();
            user.setTotalUncategorizedMiles(String.valueOf(oneDigit.format(uncategorizedMiles)));
        } else if (resources.getString(R.string.str_work).equalsIgnoreCase(prevPurpose)) {
            float workMiles = Float.parseFloat(user.getTotalBusinessMiles()) - trip.getMiles();
            user.setTotalBusinessMiles(String.valueOf(oneDigit.format(workMiles)));
        } else if (resources.getString(R.string.str_personal).equalsIgnoreCase(prevPurpose)) {
            float personalMiles = Float.parseFloat(user.getTotalPersonalMiles()) - trip.getMiles();
            user.setTotalPersonalMiles(String.valueOf(oneDigit.format(personalMiles)));
        } else if (resources.getString(R.string.str_medical).equalsIgnoreCase(prevPurpose)) {
            float medicalMiles = Float.parseFloat(user.getTotalMedicalMiles()) - trip.getMiles();
            user.setTotalMedicalMiles(String.valueOf(oneDigit.format(medicalMiles)));
        } else {
            float charityMiles = Float.parseFloat(user.getTotalCharityMiles()) - trip.getMiles();
            user.setTotalCharityMiles(String.valueOf(oneDigit.format(charityMiles)));
        }
    }


    /**
     * Gets Android device's and App's relavant information. And sets them into the given Trip.
     *
     * @param context Environment data where method was called
     * @param trip    Trip model which is going to be modified
     */
    public static void setAndroidDataToTrip(Context context, Trip trip) {
        trip.setAndroidDeviceModel(Build.MANUFACTURER + " " + Build.MODEL);
        trip.setAndroidDeviceVersion(Build.VERSION.RELEASE);
        trip.setAndroidAppVersion(getAppVersionName(context));
    }

    /**
     * Gets Android device's and App's version
     *
     * @param context Environment data where method was called
     * @return version name of the application
     */

    public static String getAppVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            return info.versionName;
        }
        return "";
    }
}
