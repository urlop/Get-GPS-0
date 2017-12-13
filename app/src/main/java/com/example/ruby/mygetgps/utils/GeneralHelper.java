package com.example.ruby.mygetgps.utils;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruby.mygetgps.GetGpsApplication;
import com.example.ruby.mygetgps.R;
import com.example.ruby.mygetgps.models.Trip;
import com.example.ruby.mygetgps.models.User;
import com.example.ruby.mygetgps.ui.activities.MainActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class GeneralHelper {

    private static final DecimalFormat oneDigit = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));//format to 1 decimal place
    private static Toast toast;

    public static void addingMilesRecentlyPostedTrip(Context context, User user, Trip trip) {
        Resources resources = context.getResources();
        if (user != null && trip != null) {
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

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    public static void sendNotification(Context context, String notificationTitle, String notificationText, int id) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(context, MainActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_stat_pointer_1915456_960_720)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(id, builder.build());
    }

    public static void showToast(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(GetGpsApplication.getInstance().getApplicationContext(),
                text,
                Toast.LENGTH_LONG);
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(30);
        toast.show();
    }
}
