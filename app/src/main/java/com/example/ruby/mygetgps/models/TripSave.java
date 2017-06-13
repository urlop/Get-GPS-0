package com.example.ruby.mygetgps.models;

import com.example.ruby.mygetgps.utils.TripHelper;
import com.orm.SugarRecord;

import java.util.List;

import timber.log.Timber;

/**
 * Trip Model for data saved in the database.
 */
public class TripSave extends SugarRecord {
    private boolean finished = false;
    private boolean autoDetected = false;
    private String mapboxUrl;
    private double miles;
    private String fromAddress;
    private String toAddress;
    private String startMethod; //"ExitGeofence", "StartButton", "AutomotiveMotion"
    private String stopMethod;  //"StopButton", "Timer", "WalkingMotion", "AppWillTerminate"
    private String fromStreet;
    private String fromSublocality;
    private String fromCity;
    private String fromSubstate;
    private String fromState;
    private String fromCountry;
    private String fromPostalCode;
    private String fromAreasOfInterest;
    private String toStreet;
    private String toSublocality;
    private String toCity;
    private String toSubstate;
    private String toState;
    private String toCountry;
    private String toPostalCode;
    private String toAreasOfInterest;

    public TripSave() {
    }

    /**
     * Get all location from Trip which are saved in the database.
     *
     * @return list of LocationSaves
     */
    public List<LocationSave> getLocations() {
        return LocationSave.find(LocationSave.class, "trip = ?", getId().toString());
    }

    /**
     * Deletes all locations from this Trip
     */
    public void deleteLocations() {
        LocationSave.deleteAll(LocationSave.class, "trip = ?", getId().toString());
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished() {
        this.finished = true;
    }

    public void setAutoDetected(boolean autoDetected) {
        this.autoDetected = autoDetected;
    }

    public void setMapboxUrl(String mapboxUrl) {
        this.mapboxUrl = mapboxUrl;
    }

    public void setMiles(float meters) {
        this.miles = TripHelper.convertMetersToMiles(meters);
    }

    public double getMiles() {
        return miles;
    }

    public String getMapboxUrl() {
        return mapboxUrl;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromStreet() {
        return fromStreet;
    }

    public void setFromStreet(String fromStreet) {
        this.fromStreet = fromStreet;
    }

    public String getFromSublocality() {
        return fromSublocality;
    }

    public void setFromSublocality(String fromSublocality) {
        this.fromSublocality = fromSublocality;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromSubstate() {
        return fromSubstate;
    }

    public void setFromSubstate(String fromSubstate) {
        this.fromSubstate = fromSubstate;
    }

    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getFromPostalCode() {
        return fromPostalCode;
    }

    public void setFromPostalCode(String fromPostalCode) {
        this.fromPostalCode = fromPostalCode;
    }

    public String getFromAreasOfInterest() {
        return fromAreasOfInterest;
    }

    public void setFromAreasOfInterest(String fromAreasOfInterest) {
        this.fromAreasOfInterest = fromAreasOfInterest;
    }

    public String getToStreet() {
        return toStreet;
    }

    public void setToStreet(String toStreet) {
        this.toStreet = toStreet;
    }

    public String getToSublocality() {
        return toSublocality;
    }

    public void setToSublocality(String toSublocality) {
        this.toSublocality = toSublocality;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getToSubstate() {
        return toSubstate;
    }

    public void setToSubstate(String toSubstate) {
        this.toSubstate = toSubstate;
    }

    public String getToState() {
        return toState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getToPostalCode() {
        return toPostalCode;
    }

    public void setToPostalCode(String toPostalCode) {
        this.toPostalCode = toPostalCode;
    }

    public String getToAreasOfInterest() {
        return toAreasOfInterest;
    }

    public void setToAreasOfInterest(String toAreasOfInterest) {
        this.toAreasOfInterest = toAreasOfInterest;
    }

    public String getStartMethod() {
        return startMethod;
    }

    public void setStartMethod(String startMethod) {
        this.startMethod = startMethod;
    }

    public String getStopMethod() {
        return stopMethod;
    }

    public void setStopMethod(String stopMethod) {
        this.stopMethod = stopMethod;
    }

    /**
     * Get all trips saved in the database ready to be uploaded.
     *
     * @return list of TripSave
     */
    public static List<TripSave> getAllTripsToUpload() {
        Timber.d("method=getAllTripsToUpload count=%d", TripSave.count(TripSave.class, "finished=?", new String[]{"1"}));
        return TripSave.find(TripSave.class, "finished=?", "1");
    }


}
