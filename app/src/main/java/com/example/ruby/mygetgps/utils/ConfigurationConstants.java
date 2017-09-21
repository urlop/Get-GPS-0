package com.example.ruby.mygetgps.utils;


/**
 * Where are constants are stored for easier access
 */
public final class ConfigurationConstants {

    //Logentries
    public static final String LOGENTRIES_TOKEN = "f679a17c-3c6b-48ca-ae54-bb232fcba88a";

    //Constants for Geofence
    public static final int GEOFENCE_RADIUS = 150; //meters
    public static final String GEOFENCE_ID = "UserGeofence";

    //Constant for Activity Detection
    public static final int ACTIVITY_CONFIDENCE = 90; //70 percentage
    public static final long DETECTION_INTERVAL_ACTIVITY = 7000;

    //Constant for Activity Detection (STOPPING)
    public static final long DETECTION_INTERVAL_ACTIVITY_STOPPING = 5 * 60 * 1000; //5 minutes

    //Intent Constant for TripTrackingService
    public static final String START_TRIP_EXTRA = "START_TRIP_EXTRA";
    public static final String START_METHOD_EXTRA = "START_METHOD_EXTRA";
    public static final String STOP_METHOD_EXTRA = "STOP_METHOD_EXTRA";

    //Constants for LocationManager
    public static final long UPDATE_INTERVAL = 5000;  /* 5 secs */
    public static final long FASTEST_INTERVAL = 3500;
    public static final float SMALLEST_DISPLACEMENT = 0f; //in meters

    //Constants for DriveAlgorithm
    public final static int DRIVE_STOP_WALKING_CONFIDENCE = 80;
    public final static int LAST_N_LOCATIONS = 40;
    public final static int LIMIT_ACCURACY = 80;
    public final static float MINIMUM_SPEED = 0.0f;
    public final static int MINIMUM_DISTANCE_DELTA = 0;
    public final static int LIMIT_DISTANCE_BETWEEN_N_LOCATIONS = 50;

    //Constants for Validate Trip
    public static final double MINIMUM_MILES = 0.125; //201.168 meters
    public static final double MINIMUM_LOCATION_POINTS = 2;

}
