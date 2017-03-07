package com.example.ruby.mygetgps.utils;

import com.example.ruby.mygetgps.models.LocationSave;
import com.example.ruby.mygetgps.models.TripSave;
import com.example.ruby.mygetgps.models.UserLocation;
import com.example.ruby.mygetgps.models.DriveState;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Helper class with methods for generating test files
 */
public class TestHelper {

    /**
     * Gets every location of the current trip; and turn them into a string. To save them later in files.
     *
     * @param driveState class where all data is extracted
     * @return string with all locations' data
     * @see com.example.ruby.mygetgps.services.on_trip.TripTrackingService#saveForTesting(DriveState, TripSave)
     */
    public static StringBuilder getAllLocations(DriveState driveState) {
        StringBuilder sb = new StringBuilder();

        sb.append("TIME")
                .append("\t | \t")
                .append("LATITUDE")
                .append("\t | \t")
                .append("LONGITUDE")
                .append("\t | \t")
                .append("LOC_ACC")
                /*.append("\t | \t")
                .append("ACCELX")
                .append("\t | \t")
                .append("ACCELY")
                .append("\t | \t")
                .append("ACCELZ")
                .append("\t | \t")
                .append("ACCEL_ACC")*/
                .append("\n");

        for (UserLocation ul : driveState.getUserLocations()) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.US);
            format.format(ul.getTimeSaved().getTime());

            sb.append(format.format(ul.getTimeSaved().getTime()))
                    .append("\t | \t")
                    .append(ul.getLocation().getLatitude())
                    .append("\t | \t")
                    .append(ul.getLocation().getLongitude())
                    .append("\t | \t")
                    .append(ul.getLocation().getAccuracy())
                    /*.append("\t | \t")
                    .append(ul.getAcceleration()[0])
                    .append("\t | \t")
                    .append(ul.getAcceleration()[1])
                    .append("\t | \t")
                    .append(ul.getAcceleration()[2])
                    .append("\t | \t")
                    .append(ul.getAccelerationAccuracy())*/
                    .append("\n");
        }

        return sb;
    }

    /**
     * Gets location saved in database of the current trip; and turn them into a string. To save them later in files.
     *
     * @param tripSave class where all data is extracted. Database table.
     * @return string with all saved locations' data
     * @see com.example.ruby.mygetgps.services.on_trip.TripTrackingService#saveForTesting(DriveState, TripSave)
     */
    public static StringBuilder getSavedLocations(TripSave tripSave) {
        StringBuilder sb = new StringBuilder();

        sb.append("LATITUDE")
                .append("\t | \t")
                .append("LONGITUDE")
                .append(System.lineSeparator());

        List<LocationSave> locationsTesting = tripSave.getLocations();

        for (LocationSave ls : locationsTesting) {
            sb.append(ls.getLatitude())
                    .append("\t | \t")
                    .append(ls.getLongitude())
                    .append(System.lineSeparator());
        }

        return sb;
    }
}
