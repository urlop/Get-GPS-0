package com.example.ruby.mygetgps.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.example.ruby.mygetgps.models.LocationSave;
import com.example.ruby.mygetgps.models.Trip;
import com.example.ruby.mygetgps.models.TripSave;
import com.example.ruby.mygetgps.utils.retrofit.CustomCallback;
import com.example.ruby.mygetgps.utils.retrofit.RequestManager;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Helper class with methods related with the current trip
 */
public class TripHelper {

    /**
     * To know if a trip was already started and has not been finished yet.
     *
     * @return trip record in database
     */
    public static TripSave tripOngoing() {
        TripSave tripSave = TripSave.last(TripSave.class);
        if (tripSave != null && !tripSave.isFinished()) {
            Timber.d("method=tripOngoing isFinished=%b id=%d", tripSave.isFinished(), tripSave.getId());
            return tripSave;
        }
        return null;
    }

    /**
     * To know if user is an {@link DetectedActivity#IN_VEHICLE} activity.
     *
     * @param detectedActivity class with current detected activity
     * @return if user is driving(true) or not(false)
     */
    public static boolean isUserDriving(DetectedActivity detectedActivity) {
        return isUserDriving(detectedActivity.getType(), detectedActivity.getConfidence());
    }

    public static boolean isUserDriving(int activityType, int activityConfidence) {
        return activityType == Constants.STARTING_DETECTED_CONDITION
                && activityConfidence > ConfigurationConstants.ACTIVITY_CONFIDENCE;
    }

    /**
     * To know if user is an {@link DetectedActivity#WALKING} activity.
     *
     * @param detectedActivity class with current detected activity
     * @return if user is walking(true) or not(false)
     */
    public static boolean isUserWalking(DetectedActivity detectedActivity) {
        return isUserWalking(detectedActivity.getType(), detectedActivity.getConfidence());
    }

    public static boolean isUserWalking(int activityType, int activityConfidence) {
        return activityType == Constants.STOPPING_DETECTED_CONDITION
                && activityConfidence > ConfigurationConstants.DRIVE_STOP_WALKING_CONFIDENCE;
    }

    /**
     * Converts a kilometers measure into miles
     *
     * @param meters distance in meters
     * @return distance in miles
     */
    public static float convertMetersToMiles(float meters) {
        double miles = meters / 1609.344;
        DecimalFormat oneDigit = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));//format to 1 decimal place
        return Float.valueOf(oneDigit.format(miles));
    }

    /**
     * Converts a number into a 2 decimals number
     *
     * @param number float number to convert
     * @return String which represents a number with two decimals
     */
    public static String convertToTwoDecimals(float number) {
        return String.format("%.2f", number);
    }

    /**
     * Gets address information based on a latitude and longitude. And sets them in the corresponding from and to columns of the TRIP_SAVE table.
     *
     * @param point   class with latitude and longitude
     * @param context Environment data where manager was called
     * @param trip    TripSave model table which is going to be modified
     * @param isFrom  boolean which indicates if the from fields must be modified. If no, to fields must be modified
     */
    public static void getAddressInfo(LatLng point, Context context, TripSave trip, boolean isFrom) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context);
            if (point.latitude != 0 || point.longitude != 0) {
                addresses = geocoder.getFromLocation(point.latitude,
                        point.longitude, 1);
                Address address = addresses.get(0);
                String addressShown = address.getAddressLine(0) + " " + address.getAddressLine(1);
                String city = address.getSubAdminArea() != null ? address.getSubAdminArea() : address.getLocality();
                if (isFrom) {
                    trip.setFromAddress(addressShown);
                    trip.setFromStreet(address.getAddressLine(0));
                    trip.setFromSublocality(address.getSubLocality());
                    trip.setFromCity(city);
                    trip.setFromSubstate(address.getSubAdminArea());
                    trip.setFromState(address.getAdminArea());
                    trip.setFromCountry(address.getCountryName());
                    trip.setFromPostalCode(address.getPostalCode());
                } else {
                    trip.setToAddress(addressShown);
                    trip.setToStreet(address.getAddressLine(0));
                    trip.setToSublocality(address.getSubLocality());
                    trip.setToCity(city);
                    trip.setToSubstate(address.getSubAdminArea());
                    trip.setToState(address.getAdminArea());
                    trip.setToCountry(address.getCountryName());
                    trip.setToPostalCode(address.getPostalCode());
                }
            }
        } catch (Exception e) {
            Timber.e(e, "method=getAddressInfo error=getAddressInfo failed");
        }
    }

    /**
     * Creates a liveCard in the Trip Fragment and notifies the adapter
     */
    public static void createLiveCard() {
        /*if (TripHelper.tripOngoing() != null) {
            if (TripTabFragment.getInstance().getNewTripCardsAdapter() != null) {
                TripTabFragment.getInstance().getNewTripCardsAdapter().addLiveCardToArray();
                TripTabFragment.getInstance().getNewTripCardsAdapter().announceDataHasChanged();
                if (TripTabFragment.getInstance().getNewTripCardsAdapter().getViewHolderLiveMap() != null) {
                    TripTabFragment.getInstance().getNewTripCardsAdapter().getViewHolderLiveMap().toggleButtons(true);
                }
            }
        }*/
    }

    /**
     * Removes a liveCard in the Trip Fragment and notifies the adapter
     */
    public static void removeLiveCard() {
        /*if (TripTabFragment.getInstance().getNewTripCardsAdapter() != null) {
            if (TripTabFragment.getInstance().getNewTripCardsAdapter().getViewHolderLiveMap() != null) {
                TripTabFragment.getInstance().getNewTripCardsAdapter().getViewHolderLiveMap().toggleButtons(false);
            }
            TripTabFragment.getInstance().getNewTripCardsAdapter().removeLiveCardFromArray();
            TripTabFragment.getInstance().getNewTripCardsAdapter().announceDataHasChanged();
        }*/
    }

    /**
     * Shows animation presenting the new created trip in the new trips tab
     *
     * @param trip    new Trip which is going to be added to the new trips tab list
     * @param context where method was called
     */
    public static void addRecentlyPostedTrip(Trip trip, Context context) {
        //Checking that autoClassify is off to show animation in new trips tab
        Timber.d("method=addRecentlyPostedTrip tripPurpose='%s'", trip.getPurpose());
        /*if (TripTabFragment.getInstance().getNewTripCardsAdapter() != null &&
                (PreferencesManager.getInstance(context).autoClassifyStateIsOff(context))) {
            TripTabFragment.getInstance().getNewTripCardsAdapter().addNewPostedTrip(trip);
            TripTabFragment.getInstance().getNewTripCardsAdapter().announceDataHasChanged();
        }
        if (TripTabFragment.getInstance().getAllTripCardsAdapter() != null) {
            TripTabFragment.getInstance().getAllTripCardsAdapter().addNewPostedTrip(trip);
            TripTabFragment.getInstance().getAllTripCardsAdapter().announceDataHasChanged();
        }

        if (TripTabFragment.getInstance().getMainActivity().getUser() != null && HomeTabFragment.getInstance().isAdded()) {
            GeneralHelper.addingMilesRecentlyPostedTrip(context, TripTabFragment.getInstance().getMainActivity().getUser(), trip);
            HomeTabFragment.getInstance().setUpPieChart();
        } else {
            TripTabFragment.getInstance().getMainActivity().retrieveUserInfo();
        }*/
    }

    /**
     * Sends information about a trip to be deleted to the ws
     *
     * @param context  Context where the method was called
     * @param trip     Trip to be deleted
     * @param doBack   boolean which tells if current activity must be finished after the call
     * @param position position of the Trip in the current list of trips
     */
    public static void deleteTrip(final Context context, final Trip trip, final boolean doBack, final int position) {
        /*Call<Trip> call = RequestManager.getDefault(context).deleteTrip(trip.getTokenId());
        call.enqueue(new CustomCallback<Trip>(context, call) {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                super.onResponse(call, response);
                if (response.isSuccessful()) {
                    if (doBack) {
                        ((Activity) context).onBackPressed();
                        TripTabFragment.getInstance().filterAllTrips();
                        TripTabFragment.getInstance().getAllTripCardsAdapter().removeStaticMap(position);
                        TripTabFragment.getInstance().getNewTripCardsAdapter().removeTripFromArray(trip.getTokenId());
                    } else {
                        TripTabFragment.getInstance().getNewTripCardsAdapter().removeStaticMap(position);
                        TripTabFragment.getInstance().getAllTripCardsAdapter().removeTripFromArray(trip.getTokenId());
                    }
                }
            }
        });*/
    }

    public static boolean validateFinalTrip(TripSave tripSave) {
        return tripSave.getMiles() >= ConfigurationConstants.MINIMUM_MILES && tripSave.getLocations().size() >= ConfigurationConstants.MINIMUM_LOCATION_POINTS;
    }

    /**
     * Creates Geofence in the location of the user.
     * If user exits, trill will start.
     *
     * @return Geofence created
     * @see Geofence
     */
    public static Geofence createGeofence(Location mLastKnowLocation) {
        return new Geofence.Builder()
                .setRequestId(ConfigurationConstants.GEOFENCE_ID)
                .setCircularRegion(mLastKnowLocation.getLatitude(), mLastKnowLocation.getLongitude(), ConfigurationConstants.GEOFENCE_RADIUS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_EXIT).build();
    }

    /**
     * Converts all locations into a list of strings in a format useful by webservice
     *
     * @param locationSaves saved locations which contains all latitudes and longitudes
     * @return list of strings ready to be sent to the webservice
     */
    public static List<String> locationsToString(List<LocationSave> locationSaves) {
        List<String> locationsString = new ArrayList<>();
        for (LocationSave ls : locationSaves) {
            locationsString.add(ls.getLatitude() + "," + ls.getLongitude());
        }
        return locationsString;
    }

    /**
     * Sends trip updated data to the ws.
     *
     * @param context     Context where the method was called
     * @param trip        trip with updated data to be uploaded
     * @param prevPurpose previous purpose of the trip
     */
    public static void updateTrip(final Context context, Trip trip, final String prevPurpose) {
        /*final String tripId = trip.getTokenId();
        Call<Trip> call = RequestManager.getDefault(context).updateTrip(tripId, trip);
        call.enqueue(new CustomCallback<Trip>(context, call) {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                super.onResponse(call, response);
                if (response.isSuccessful()) {
                    GeneralHelper.onSwipeAllUpdateUserMiles(context, response.body(), TripTabFragment.getInstance().getMainActivity().getUser(), prevPurpose);
                    if (prevPurpose == null) { //if unclassified is changed remove from new tab
                        if (TripTabFragment.getInstance().getNewTripCardsAdapter() != null) {
                            TripTabFragment.getInstance().getNewTripCardsAdapter().removeStaticMap(tripId);
                        }
                    }
                    if (TripTabFragment.getInstance().getMainActivity().getUser() != null && HomeTabFragment.getInstance().isAdded()) {
                        HomeTabFragment.getInstance().setUpPieChart();
                    }
                }
            }
        });*/
    }
}
