package com.example.ruby.mygetgps.services.upload_trip;

import android.app.IntentService;
import android.content.Intent;

import com.example.ruby.mygetgps.models.LocationSave;
import com.example.ruby.mygetgps.models.RecordWS;
import com.example.ruby.mygetgps.models.Trip;
import com.example.ruby.mygetgps.models.TripSave;
import com.example.ruby.mygetgps.models.TripWS;
import com.example.ruby.mygetgps.models.network.RecordBody;
import com.example.ruby.mygetgps.models.network.TripBody;
import com.example.ruby.mygetgps.utils.Constants;
import com.example.ruby.mygetgps.utils.GeneralHelper;
import com.example.ruby.mygetgps.utils.PreferencesManager;
import com.example.ruby.mygetgps.utils.ServiceHelper;
import com.example.ruby.mygetgps.utils.TimeHelper;
import com.example.ruby.mygetgps.utils.TripHelper;
import com.example.ruby.mygetgps.utils.retrofit.RequestManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Service for uploading missing data to the web service.
 * Uploads all finished trips which could not be uploaded before.
 * If it fails the first time, tries again. (NOT ANYMORE)
 */
public class UploadService extends IntentService {
    public static final String ACTION = "com.everlance.UploadService.SendTrip";
    private TripSave currentTripSave;

    TripWS tripSent;

    public UploadService() {
        super(UploadService.class.getName());
    }

    /**
     * Uploads trips not uploaded yet to the web service.
     * TRIP_ID_EXTRA is the id of the current finished trip if there is one.
     *
     * @param intent value passed to startService(Intent).
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra(Constants.TRIP_ID_EXTRA)) {
                currentTripSave = getTripSaved(intent.getLongExtra(Constants.TRIP_ID_EXTRA, 0L));
                if (currentTripSave != null) {
                    Timber.i("method=onHandleIntent currentTripSave='not null'");
                    generateMissingData(currentTripSave.getLocations());
                } else {
                    Timber.w("method=onHandleIntent message='Trip not found in Local DB'");
                }
            }
            uploadAllMissingTrips();
        }
    }

    /**
     * Gets recent trip saved
     *
     * @param id recent trip's identifier
     * @return TripSave class
     * @see TripSave
     */
    private TripSave getTripSaved(Long id) {
        Timber.i("method=getTripSaved id=%d", id);
        Timber.i("method=getTripSaved tripsNumber=%d", TripSave.count(TripSave.class));
        TripSave tripSave = TripSave.findById(TripSave.class, id);
        if (tripSave != null) {
            Timber.i("method=getTripSaved tripSave.id=%d", tripSave.getId());
            return tripSave;
        } else if (TripSave.last(TripSave.class) != null) {
            Timber.i("method=getTripSaved lastTripSaved.id=%d", TripSave.last(TripSave.class).getId());
            return TripSave.last(TripSave.class);
        } else {
            Timber.i("method=getTripSaved tripSave=null");
            return null;
        }
    }

    /**
     * If user has wifi connection, calls web service to send all trips not uploaded yet.
     *
     * @see #hasConnection()
     */
    private void uploadAllMissingTrips() {
        List<TripSave> trips = TripSave.getAllTripsToUpload();
        for (TripSave ts : trips) {
            callUploadTrip(ts);
        }
    }

    /**
     * Tells if user has connection
     *
     * @return true if a connection is detected
     */
    //TODO: Check if it is going to be used
    private boolean hasConnection() {
        return true;
    }

    /**
     * Uploads tripSave to web service.
     *
     * @param tripSave to to be uploaded
     */
    private void callUploadTrip(final TripSave tripSave) {
        Timber.d("method=callUploadTrip");
        if (!tripSave.getLocations().isEmpty() && tripSave.getLocations().size() > 1) {
            TripWS tripWS = new TripWS();
            tripWS.setVehicle_type_id(PreferencesManager.getInstance(this).getVehicleType());

            /*Trip trip = new Trip((float) (tripSave.getMiles()), true,
                    tripSave.getMapboxUrl(), tripSave.getFromAddress(), tripSave.getToAddress(), TripHelper.locationsToString(tripSave.getLocations()),
                    tripSave.getStartMethod(), tripSave.getStopMethod(),
                    tripSave.getFromStreet(), tripSave.getFromSublocality(), tripSave.getFromCity(), tripSave.getFromSubstate(), tripSave.getFromState(), tripSave.getFromCountry(), tripSave.getFromPostalCode(),
                    tripSave.getToStreet(), tripSave.getToSublocality(), tripSave.getToCity(), tripSave.getToSubstate(), tripSave.getToState(), tripSave.getToCountry(), tripSave.getToPostalCode());

            if (!PreferencesManager.getInstance(getApplicationContext()).autoClassifyStateIsOff(getApplicationContext())) {
                trip.setPurpose(PreferencesManager.getInstance(getApplicationContext()).getAutoClassifyState());
            } else {
                trip.setPurpose(null);
            }
            trip.setStartedAt(TimeHelper.longToTimeFormat(tripSave.getLocations().get(0).getTime()));
            trip.setEndedAt(TimeHelper.longToTimeFormat(tripSave.getLocations().get(tripSave.getLocations().size() - 1).getTime()));
            GeneralHelper.setAndroidDataToTrip(this, trip);*/

            RequestManager.getDefault(getApplicationContext()).uploadTrip(tripWS.getVehicle_type_id()).enqueue(new Callback<TripWS>() {
                @Override
                public void onResponse(Call<TripWS> call, Response<TripWS> response) {
                    if (response.isSuccessful()) {
                        Timber.d("method=onResponse action='Trip saved in WS'");
                        //tripSave.deleteLocations();
                        //tripSave.delete();
                        tripSent = response.body();
                        //ServiceHelper.broadCastTripStopped(getApplicationContext(), trip);
                        //TripTabFragment.getInstance().setProgressBarVisibility(false);
                        sendRecords(tripSave);
                    }
                    //TODO handle server error
                }

                @Override
                public void onFailure(Call<TripWS> call, Throwable t) {
                    Timber.d("method=onFailure");
                    //TripTabFragment.getInstance().setProgressBarVisibility(false);
                }

            });
        }
    }

    private void sendRecords(final TripSave tripSave) {
        final List<LocationSave> locations = tripSave.getLocations();
        for (int i = 0; i < locations.size() - 1; i++) {
            LocationSave locationSave = locations.get(i);
            LocationSave nextLocationSave = locations.get(i + 1);
            RecordWS recordWS = new RecordWS();
            recordWS.setTravel_id(tripSent.getId());
            recordWS.setStart_latitude((float) locationSave.getLatitude());
            recordWS.setStart_longitude((float) locationSave.getLongitude());
            recordWS.setEnd_latitude((float) nextLocationSave.getLatitude());
            recordWS.setEnd_longitude((float) nextLocationSave.getLongitude());

            //Set speed
            double speed;
            if (locationSave.getSpeed() == 0) {
                speed = Math.sqrt(
                        Math.pow(nextLocationSave.getLongitude() - locationSave.getLongitude(), 2)
                                + Math.pow(nextLocationSave.getLatitude() - locationSave.getLatitude(), 2)
                ) / (nextLocationSave.getTime() - locationSave.getTime());
                speed = (double) Math.round(speed * 10000000d) / 10000000d;
            } else {
                speed = locationSave.getSpeed();
            }
            recordWS.setSpeed((float) speed);
            Date dateRegistered = new Date();
            dateRegistered.setTime(locationSave.getTime());
            recordWS.setTime_registered(dateRegistered);

            final int finalI = i;
            RequestManager.getDefault(getApplicationContext()).uploadRecord(
                    recordWS.getTravel_id(),
                    recordWS.getStart_latitude(),
                    recordWS.getStart_longitude(),
                    recordWS.getEnd_latitude(),
                    recordWS.getEnd_longitude(),
                    recordWS.getSpeed(),
                    recordWS.getTime_registered()
            ).enqueue(new Callback<RecordWS>() {
                @Override
                public void onResponse(Call<RecordWS> call, Response<RecordWS> response) {
                    if (response.isSuccessful()) {
                        Timber.d("method=sendRecords.onResponse action='Trip saved in WS'");
                        if (finalI == locations.size() - 2) {
                            tripSave.deleteLocations();
                            tripSave.delete();
                        }
                        RecordWS record = response.body();
                    }
                }

                @Override
                public void onFailure(Call<RecordWS> call, Throwable t) {
                    Timber.d("method=sendRecords.onFailure");
                    //TripTabFragment.getInstance().setProgressBarVisibility(false);
                }

            });
        }
    }

    /**
     * Generates missing information fro trip.
     *
     * @param locationSaves Trip's locations
     */
    private void generateMissingData(List<LocationSave> locationSaves) {
        List<LatLng> points = new ArrayList<>();

        for (LocationSave locationSave : locationSaves) {
            points.add(new LatLng(locationSave.getLatitude(), locationSave.getLongitude()));
        }

        if (points.size() > 0) {
            LatLng firstPoint = points.get(0);
            LatLng lastPoint = points.get(points.size() - 1);

            TripHelper.getAddressInfo(firstPoint, getApplicationContext(), currentTripSave, true);
            TripHelper.getAddressInfo(lastPoint, getApplicationContext(), currentTripSave, false);

            String mapboxUrl = "";//MapboxHelper.buildMapUrl(points, firstPoint, lastPoint);
            currentTripSave.setMapboxUrl(mapboxUrl);
            currentTripSave.setFinished();

            currentTripSave.save();
        }
    }

}


