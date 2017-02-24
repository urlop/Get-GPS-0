package com.example.ruby.mygetgps.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class UncategorizedTrip implements Serializable {

    @SerializedName("token_id")
    @Expose
    private String tokenId;
    @SerializedName("miles")
    @Expose
    private Float miles;
    @SerializedName("date_formatted")
    @Expose
    private String dateFormatted;
    @SerializedName("purpose")
    @Expose
    private Object purpose;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("savings")
    @Expose
    private String savings;
    @SerializedName("locations")
    @Expose
    private List<String> locations = new ArrayList<>();
    @SerializedName("deduction")
    @Expose
    private String deduction;
    @SerializedName("auto_detected")
    @Expose
    private Boolean autoDetected;
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
    @SerializedName("mapbox_url")
    @Expose
    private String mapboxUrl;
    @SerializedName("income_source")
    @Expose
    private Object incomeSource;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("kms")
    @Expose
    private String kms;
    @SerializedName("rate")
    @Expose
    private Float rate;
    @SerializedName("calculate_route_enabled")
    @Expose
    private Boolean calculateRouteEnabled;
    @SerializedName("photo")
    @Expose
    private Object photo;


    public UncategorizedTrip() {

    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Float getMiles() {
        return miles;
    }

    public void setMiles(Float miles) {
        this.miles = miles;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
    }

    public Object getPurpose() {
        return purpose;
    }

    public void setPurpose(Object purpose) {
        this.purpose = purpose;
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

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }

    public Boolean getAutoDetected() {
        return autoDetected;
    }

    public void setAutoDetected(Boolean autoDetected) {
        this.autoDetected = autoDetected;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getStartedAtCoordinate() {
        return startedAtCoordinate;
    }

    public void setStartedAtCoordinate(String startedAtCoordinate) {
        this.startedAtCoordinate = startedAtCoordinate;
    }

    public String getEndedAtCoordinate() {
        return endedAtCoordinate;
    }

    public void setEndedAtCoordinate(String endedAtCoordinate) {
        this.endedAtCoordinate = endedAtCoordinate;
    }

    public String getMapboxUrl() {
        return mapboxUrl;
    }

    public void setMapboxUrl(String mapboxUrl) {
        this.mapboxUrl = mapboxUrl;
    }

    public Object getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(Object incomeSource) {
        this.incomeSource = incomeSource;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public String getKms() {
        return kms;
    }

    public void setKms(String kms) {
        this.kms = kms;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Boolean getCalculateRouteEnabled() {
        return calculateRouteEnabled;
    }

    public void setCalculateRouteEnabled(Boolean calculateRouteEnabled) {
        this.calculateRouteEnabled = calculateRouteEnabled;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }
}
