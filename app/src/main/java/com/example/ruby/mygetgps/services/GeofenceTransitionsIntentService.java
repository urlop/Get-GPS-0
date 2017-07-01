/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ruby.mygetgps.services;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.example.ruby.mygetgps.services.on_trip.TripTrackingService;
import com.example.ruby.mygetgps.utils.Constants;
import com.example.ruby.mygetgps.ui.activities.MainActivity;
import com.example.ruby.mygetgps.R;
import com.example.ruby.mygetgps.utils.GeneralHelper;
import com.example.ruby.mygetgps.utils.ServiceHelper;
import com.example.ruby.mygetgps.utils.TripHelper;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Listener for geofence transition changes.
 *
 * Receives geofence transition events from Location Services in the form of an Intent containing
 * the transition type and geofence id(s) that triggered the transition. Creates a notification
 * as the output.
 */
public class GeofenceTransitionsIntentService extends IntentService {

    protected static final String TAG = "GeofenceTransitionsIS";

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public GeofenceTransitionsIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Handles incoming intents.
     * @param intent sent by Location Services. This Intent is provided to Location
     *               Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (!geofencingEvent.hasError()) {
            // Get the transition type.
            int geofenceTransition = geofencingEvent.getGeofenceTransition();

            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                    geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

                // Get the geofences that were triggered. A single event can trigger multiple geofences.
                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

                // Get the transition details as a String.
                String geofenceTransitionDetails = getGeofenceTransitionDetails(
                        this,
                        geofenceTransition,
                        triggeringGeofences
                );

                // Send notification and log the transition details.
                /*GeneralHelper.sendNotification(getApplicationContext(),
                        geofenceTransitionDetails,
                        getApplicationContext().getString(R.string.geofence_transition_notification_text),
                        0);*/
                Log.i(TAG, geofenceTransitionDetails);

                //TODO Check if needs geofence.
                /*if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT && !ServiceHelper.isServiceRunning(this, TripTrackingService.class)){
                    GeneralHelper.sendNotification(getApplicationContext(),
                            getApplicationContext().getString(R.string.notification_geofence_exited),
                            getApplicationContext().getString(R.string.geofence_transition_notification_text),
                            0);
                    ServiceHelper.startTripTrackingService(this, null);
                }*/
            } else {
                // Log the error.
                Log.e(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition));
            }
        }

        if (ActivityRecognitionResult.hasResult(intent)) {  //MOTION TRACKING
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            DetectedActivity detectedActivity = result.getMostProbableActivity();

            if (TripHelper.isUserDriving(detectedActivity)) {
                if (!ServiceHelper.isServiceRunning(this, TripTrackingService.class)) {
                    GeneralHelper.sendNotification(getApplicationContext(),
                            getApplicationContext().getString(R.string.notification_activity_driving),
                            getApplicationContext().getString(R.string.geofence_transition_notification_text),
                            0);
                    ServiceHelper.startTripTrackingService(getApplicationContext(), Constants.DRIVING_MOTION); //startMethod: "AutomotiveMotion"
                }
            }
        }
    }

    /**
     * Gets transition details and returns them as a formatted string.
     *
     * @param context               The app context.
     * @param geofenceTransition    The ID of the geofence transition.
     * @param triggeringGeofences   The geofence(s) triggered.
     * @return                      The transition details formatted as String.
     */
    private String getGeofenceTransitionDetails(
            Context context,
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);

        return geofenceTransitionString; // + ": " + triggeringGeofencesIdsString;
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType    A transition type constant defined in Geofence
     * @return                  A String indicating the type of transition
     */
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }


}
