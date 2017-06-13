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

package com.example.ruby.mygetgps.utils;

import android.Manifest;

import com.example.ruby.mygetgps.BuildConfig;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Constants used in this sample.
 */
public final class Constants {

    private Constants() {
    }

    public static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 100;//40;//1609; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<String, LatLng>();
    static {
        // San Francisco International Airport.
        BAY_AREA_LANDMARKS.put("SFO", new LatLng(37.621313, -122.378955));

        // Googleplex.
        BAY_AREA_LANDMARKS.put("GOOGLE", new LatLng(37.422611,-122.0840577));

        BAY_AREA_LANDMARKS.put("HM", new LatLng(-12.044849, -77.031235));
    }



    // Build configs
    public static final String BASE_API_URL = BuildConfig.BASE_API_URL;
    public static final boolean CRASHLYTICS_DISABLED = BuildConfig.CRASHLYTICS_DISABLED;
    public static final boolean DEBUG_LOG = BuildConfig.DEBUG;
    public static final boolean MUST_GET_MOTION_TO_STOP = BuildConfig.MUST_GET_MOTION_TO_STOP;

    //Intent Constant for activityInformationToTripTrackingService
    public static final String ACTIVITY_TYPE_EXTRA = "ActivityType";
    public static final String ACTIVITY_CONFIDENCE_EXTRA = "ActivityConfidence";

    //Intent Constant for broadcasting TripTrackingService
    public static final String LATITUDE_EXTRA = "latitude";
    public static final String LONGITUDE_EXTRA = "longitude";
    public static final String DISTANCE_EXTRA = "distance";

    //Intent Constant for EditProfile
    public static final int EDIT_PROFILE = 2;
    public static final String EDIT_PROFILE_EXTRA = "EditProfile";
    public static final String EDIT_PROFILE_USER_EXTRA = "User";

    //Intent Constant for Switch Work
    public static final int TRIP_WORK_SOURCE = 3;
    public static final String TRIP_WORK_SOURCE_USER_EXTRA = "User";
    public static final String TRIP_WORK_SOURCE_TRIP_EXTRA = "Trip";
    public static final String TRIP_WORK_SOURCE_STRING_EXTRA = "WorkSourceString";
    public static final String TRIP_WORK_SOURCE_PREVIOUS_TRIP_PURPOSE_EXTRA = "TripPurpose";
    public static final String TRIP_WORK_SOURCE_ORIGIN_EXTRA = "Origin";

    public static final int ORIGIN_ADD_TRIP_ACTIVITY = 10;

    //Intent Constant for StartTripReceiver
    public static final String DATE_FROM_TRIP_EXTRA = "dateTrip";

    //Intent Constant for UploadService
    public static final String TRIP_ID_EXTRA = "savedLocations";

    //Permissions
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 20;
    public static final String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    //Constant for Mapbox Static Map
    public static final String MAPBOX_API_KEY = "pk.eyJ1Ijoid2lsbHlyaDQ5NSIsImEiOiJjaWpwd2tka3MwMWYzdDlrb2w5bDcycjZ0In0.eOcaLDji1e5DP2b5NBFRjw";

    //MainActivity Constant for requesting fine location
    public static final int REQUEST_CHECK_SETTINGS = 0x1;

    //Constant for StartTripService
    public static final String START_TRIP_DATE_EXTRA = "startTripDate";

    //Constant for NewTripCardsAdapter
    public static final int TRIP_CARD_VIEW_TYPE_LIVE = 0;
    public static final int TRIP_CARD_VIEW_TYPE_STATIC = 1;

    //Constant for AllTripCardsAdapter
    public static final String VIEW_HOLDER_POSITION = "ViewHolderPosition";

    //Intent Constant for UploadService
    public static final String TRIP_OBJECT_EXTRA = "tripObjectExtra";

    //DetectedActivity
    public static final int STOPPING_DETECTED_CONDITION = DetectedActivity.ON_FOOT;
    public static final int STARTING_DETECTED_CONDITION = DetectedActivity.IN_VEHICLE;

    public static final String IMAGE_FILE = "image/*";
    //Start Trip Methods
    public static final String EXIT_GEOFENCE = "ExitGeofence";
    public static final String START_BUTTON = "StartButton";
    public static final String DRIVING_MOTION = "AutomotiveMotion";
    //Stop Trip Methods
    public static final String STOP_BUTTON = "StopButton";
    public static final String WALKING_MOTION = "WalkingMotion";
    public final static String TIMER = "Timer";
    public static final String APP_WILL_TERMINATE = "AppWillTerminate";

    //ANIMATION SWIPE
    public static final int ANIMATION_DURATION = 500;
    public static final int ANIMATION_DELAY = 100;
}
