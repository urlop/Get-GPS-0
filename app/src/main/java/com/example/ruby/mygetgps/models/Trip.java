
package com.example.ruby.mygetgps.models;

import com.example.ruby.mygetgps.utils.TripHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Trip Model for sending data to the web service.
 */
public class Trip implements Serializable {

    @SerializedName("token_id")
    @Expose
    private String tokenId;

    @SerializedName("miles")
    @Expose
    private float miles;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("mapbox_url")
    @Expose
    private String mapboxUrl;

    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("to")
    @Expose
    private String to;

    @SerializedName("purpose")
    @Expose
    private String purpose;

    @SerializedName("notes")
    @Expose
    private String notes;

    @SerializedName("photo_attributes")
    @Expose
    private PhotoAttributes photoAttributes;

    @SerializedName("auto_detected")
    @Expose
    private boolean autoDetected;

    @SerializedName("income_source")
    @Expose
    private String incomeSource;

    @SerializedName("started_at")
    @Expose
    private String startedAt;

    @SerializedName("ended_at")
    @Expose
    private String endedAt;

    @SerializedName("started_at_coordinate")
    @Expose
    private String startedAtCoordinate;

    @SerializedName("ended_at_coordinate")
    @Expose
    private String endedAtCoordinate;

    @SerializedName("date_formatted")
    @Expose
    private String dateFormatted;

    @SerializedName("locations")
    @Expose
    private List<String> locations;

    @SerializedName("deduction")
    @Expose
    private String deduction;


    @SerializedName("android_device_model")
    @Expose
    private String androidDeviceModel;

    @SerializedName("android_device_version")
    @Expose
    private String androidDeviceVersion;

    @SerializedName("android_app_version")
    @Expose
    private String androidAppVersion;

    @SerializedName("start_method")
    @Expose
    private String startMethod; //"ExitGeofence", "StartButton", "AutomotiveMotion"

    @SerializedName("stop_method")
    @Expose
    private String stopMethod;  //"StopButton", "Timer", "WalkingMotion", "AppWillTerminate"


    @SerializedName("from_street")
    @Expose
    private String fromStreet;

    @SerializedName("from_sublocality")
    @Expose
    private String fromSublocality;

    @SerializedName("from_city")
    @Expose
    private String fromCity;

    @SerializedName("from_substate")
    @Expose
    private String fromSubstate;

    @SerializedName("from_state")
    @Expose
    private String fromState;

    @SerializedName("from_country")
    @Expose
    private String fromCountry;

    @SerializedName("from_postal_code")
    @Expose
    private String fromPostalCode;

    @SerializedName("from_areas_of_interest")
    @Expose
    private String fromAreasOfInterest;

    @SerializedName("to_street")
    @Expose
    private String toStreet;

    @SerializedName("to_sublocality")
    @Expose
    private String toSublocality;

    @SerializedName("to_city")
    @Expose
    private String toCity;

    @SerializedName("to_substate")
    @Expose
    private String toSubstate;

    @SerializedName("to_state")
    @Expose
    private String toState;

    @SerializedName("to_country")
    @Expose
    private String toCountry;

    @SerializedName("to_postal_code")
    @Expose
    private String toPostalCode;

    @SerializedName("to_areas_of_interest")
    @Expose
    private String toAreasOfInterest;


    /**
     * Complete constructor
     *
     * @param miles        miles traveled
     * @param autoDetected if trip was started automatically
     * @param mapboxUrl    speed of the user at that moment. For testing purposes.
     * @param from         user's acceleration in axis X. For testing purposes.
     * @param to           user's acceleration in axis Y. For testing purposes.
     * @param locations    list of trip's locations
     */
    public Trip(float miles, boolean autoDetected, String mapboxUrl, String from, String to, List<String> locations) {
        this.miles = miles;
        this.autoDetected = autoDetected;
        this.mapboxUrl = mapboxUrl;
        this.from = from;
        this.to = to;
        this.locations = locations;
    }

    public Trip(float miles, boolean autoDetected, String mapboxUrl, String from, String to, List<String> locations, String startMethod, String stopMethod, String fromStreet, String fromSublocality, String fromCity, String fromSubstate, String fromState, String fromCountry, String fromPostalCode, String toStreet, String toSublocality, String toCity, String toSubstate, String toState, String toCountry, String toPostalCode) {
        this.miles = miles;
        this.autoDetected = autoDetected;
        this.mapboxUrl = mapboxUrl;
        this.from = from;
        this.to = to;
        this.locations = locations;
        this.startMethod = startMethod;
        this.stopMethod = stopMethod;
        this.fromStreet = fromStreet;
        this.fromSublocality = fromSublocality;
        this.fromCity = fromCity;
        this.fromSubstate = fromSubstate;
        this.fromState = fromState;
        this.fromCountry = fromCountry;
        this.fromPostalCode = fromPostalCode;
        this.toStreet = toStreet;
        this.toSublocality = toSublocality;
        this.toCity = toCity;
        this.toSubstate = toSubstate;
        this.toState = toState;
        this.toCountry = toCountry;
        this.toPostalCode = toPostalCode;
    }

    public float getMiles() {
        return miles;
    }

    public String getMapboxUrl() {
        return mapboxUrl;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isAutoDetected() {
        return autoDetected;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public String getStartedAtCoordinate() {
        return startedAtCoordinate;
    }

    public String getEndedAtCoordinate() {
        return endedAtCoordinate;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getDeduction() {
        return TripHelper.convertToTwoDecimals(Math.abs(Float.parseFloat(deduction)));
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public void setMiles(float miles) {
        this.miles = miles;
    }

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
    }

    public void setPhotoAttributes(PhotoAttributes photoAttributes) {
        this.photoAttributes = photoAttributes;
    }

    public PhotoAttributes getPhotoAttributes() {
        return photoAttributes;
    }
    public void setAndroidDeviceModel(String androidDeviceModel) {
        this.androidDeviceModel = androidDeviceModel;
    }

    public void setAndroidDeviceVersion(String androidDeviceVersion) {
        this.androidDeviceVersion = androidDeviceVersion;
    }

    public void setAndroidAppVersion(String androidAppVersion) {
        this.androidAppVersion = androidAppVersion;
    }

    public void setIncomeSource(String incomeSource) {
        this.incomeSource = incomeSource;
    }
}