package com.example.ruby.mygetgps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password_confirmation")
    @Expose
    private String passwordConfirmation;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("token_id")
    @Expose
    private String tokenId;

    @SerializedName("auth_token")
    @Expose
    private String authToken;

    @SerializedName("invite_code")
    @Expose
    private String inviteCode;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("last")
    @Expose
    private String last;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("average_tax_rate")
    @Expose
    private String averageTaxRate;

    @SerializedName("auto_detect_enabled")
    @Expose
    private Boolean autoDetectEnabled;

    @SerializedName("income_sources")
    @Expose
    private List<String> incomeSources;

    @SerializedName("custom_expense_categories")
    @Expose
    private List<Object> customExpenseCategories;

    @SerializedName("auto_classify")
    @Expose
    private String autoClassify;

    @SerializedName("kms_enabled")
    @Expose
    private Boolean kmsEnabled;


    @SerializedName("custom_mileage_rate")
    @Expose
    private Float customMileageRate;


    @SerializedName("total_revenue")
    @Expose
    private String totalRevenue;

    @SerializedName("total_expenses")
    @Expose
    private String totalExpenses;

    @SerializedName("total_trips_deductions")
    @Expose
    private String totalTripsDeductions;

    @SerializedName("total_standard_method_expenses")
    @Expose
    private String totalStandardMethodExpenses;

    @SerializedName("total_standard_method_revenue")
    @Expose
    private String totalStandardMethodRevenue;

    @SerializedName("total_miles")
    @Expose
    private String totalMiles;

    @SerializedName("total_uncategorized_miles")
    @Expose
    private String totalUncategorizedMiles;

    @SerializedName("total_business_miles")
    @Expose
    private String totalBusinessMiles;

    @SerializedName("total_personal_miles")
    @Expose
    private String totalPersonalMiles;

    @SerializedName("total_medical_miles")
    @Expose
    private String totalMedicalMiles;

    @SerializedName("total_charity_miles")
    @Expose
    private String totalCharityMiles;

    @SerializedName("total_kms")
    @Expose
    private String totalKms;

    @SerializedName("total_uncategorized_kms")
    @Expose
    private String totalUncategorizedKms;

    @SerializedName("total_business_kms")
    @Expose
    private String totalBusinessKms;

    @SerializedName("total_personal_kms")
    @Expose
    private String totalPersonalKms;

    @SerializedName("total_medical_kms")
    @Expose
    private String totalMedicalKms;

    @SerializedName("total_charity_kms")
    @Expose
    private String totalCharityKms;

    @SerializedName("unique_uncategorized_trip_dates")
    @Expose
    private List<String> uniqueUncategorizedTripDates;

    @SerializedName("unique_trip_dates")
    @Expose
    private List<String> uniqueTripDates;

    @SerializedName("unique_transaction_dates")
    @Expose
    private List<String> uniqueTransactionDates;

    @SerializedName("is_last_uncategorized_trips_page")
    @Expose
    private Boolean isLastUncategorizedTripsPage;

    @SerializedName("is_last_trips_page")
    @Expose
    private Boolean isLastTripsPage;

    @SerializedName("is_last_transactions_page")
    @Expose
    private Boolean isLastTransactionsPage;

    @SerializedName("referral_code_name")
    @Expose
    private String referralCodeName;

    @SerializedName("referral_code_attributes")
    @Expose
    private ReferralCodeAttributes referralCodeAttributes;

    @SerializedName("trips")
    @Expose
    private List<Trip> trips;

    @SerializedName("uncategorized_trips")
    @Expose
    private List<Trip> uncategorizedTrips;


    public User() {

    }

    public User(String email, String passwordConfirmation, String password, String tokenId, String authToken, String inviteCode, String name, String last, String mobile, String averageTaxRate,
                String totalRevenue, String totalExpenses, String totalTripsDeductions, String totalStandardMethodExpenses, String totalStandardMethodRevenue, String totalMiles, String totalUncategorizedMiles, String totalBusinessMiles, String totalPersonalMiles, String totalMedicalMiles, String totalCharityMiles, String totalKms, String totalUncategorizedKms, String totalBusinessKms, String totalPersonalKms, String totalMedicalKms, String totalCharityKms,
                String referralCodeName,
                String autoClassify,
                Boolean autoDetectEnabled,
                Boolean kmsEnabled,
                Boolean isLastUncategorizedTripsPage, Boolean isLastTripsPage, Boolean isLastTransactionsPage,
                Float customMileageRate,
                ReferralCodeAttributes referralCodeAttributes,
                List<String> incomeSources, List<Object> customExpenseCategories,
                List<String> uniqueUncategorizedTripDates, List<String> uniqueTripDates, List<String> uniqueTransactionDates,
                List<Trip> trips, List<Trip> uncategorizedTrips) {
        this.email = email;
        this.passwordConfirmation = passwordConfirmation;
        this.password = password;
        this.tokenId = tokenId;
        this.authToken = authToken;
        this.inviteCode = inviteCode;
        this.name = name;
        this.last = last;
        this.mobile = mobile;
        this.averageTaxRate = averageTaxRate;
        this.autoDetectEnabled = autoDetectEnabled;
        this.incomeSources = incomeSources;
        this.customExpenseCategories = customExpenseCategories;
        this.autoClassify = autoClassify;
        this.kmsEnabled = kmsEnabled;
        this.customMileageRate = customMileageRate;
        this.totalRevenue = totalRevenue;
        this.totalExpenses = totalExpenses;
        this.totalTripsDeductions = totalTripsDeductions;
        this.totalStandardMethodExpenses = totalStandardMethodExpenses;
        this.totalStandardMethodRevenue = totalStandardMethodRevenue;
        this.totalMiles = totalMiles;
        this.totalUncategorizedMiles = totalUncategorizedMiles;
        this.totalBusinessMiles = totalBusinessMiles;
        this.totalPersonalMiles = totalPersonalMiles;
        this.totalMedicalMiles = totalMedicalMiles;
        this.totalCharityMiles = totalCharityMiles;
        this.totalKms = totalKms;
        this.totalUncategorizedKms = totalUncategorizedKms;
        this.totalBusinessKms = totalBusinessKms;
        this.totalPersonalKms = totalPersonalKms;
        this.totalMedicalKms = totalMedicalKms;
        this.totalCharityKms = totalCharityKms;
        this.uniqueUncategorizedTripDates = uniqueUncategorizedTripDates;
        this.uniqueTripDates = uniqueTripDates;
        this.uniqueTransactionDates = uniqueTransactionDates;
        this.isLastUncategorizedTripsPage = isLastUncategorizedTripsPage;
        this.isLastTripsPage = isLastTripsPage;
        this.isLastTransactionsPage = isLastTransactionsPage;
        this.referralCodeName = referralCodeName;
        this.referralCodeAttributes = referralCodeAttributes;
        this.trips = trips;
        this.uncategorizedTrips = uncategorizedTrips;
    }

    public void setTotalUncategorizedMiles(String totalUncategorizedMiles) {
        this.totalUncategorizedMiles = totalUncategorizedMiles;
    }

    public void setTotalBusinessMiles(String totalBusinessMiles) {
        this.totalBusinessMiles = totalBusinessMiles;
    }

    public void setTotalPersonalMiles(String totalPersonalMiles) {
        this.totalPersonalMiles = totalPersonalMiles;
    }

    public void setTotalMedicalMiles(String totalMedicalMiles) {
        this.totalMedicalMiles = totalMedicalMiles;
    }

    public void setTotalCharityMiles(String totalCharityMiles) {
        this.totalCharityMiles = totalCharityMiles;
    }

    public ReferralCodeAttributes getReferralCodeAttributes() {
        return referralCodeAttributes;
    }

    public void setReferralCodeAttributes(ReferralCodeAttributes referralCodeAttributes) {
        this.referralCodeAttributes = referralCodeAttributes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public List<String> getIncomeSources() {
        return incomeSources;
    }

    public void setIncomeSources(List<String> incomeSources) {
        this.incomeSources = incomeSources;
    }


    public String getName() {
        return name;
    }

    public String getLast() {
        return last;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAverageTaxRate() {
        return averageTaxRate;
    }

    public Boolean getAutoDetectEnabled() {
        return autoDetectEnabled;
    }

    public List<Object> getCustomExpenseCategories() {
        return customExpenseCategories;
    }

    public String getAutoClassify() {
        return autoClassify;
    }

    public Boolean getKmsEnabled() {
        return kmsEnabled;
    }

    public Float getCustomMileageRate() {
        return customMileageRate;
    }

    public String getTotalRevenue() {
        return totalRevenue;
    }

    public String getTotalExpenses() {
        return totalExpenses;
    }

    public String getTotalTripsDeductions() {
        return totalTripsDeductions;
    }

    public String getTotalStandardMethodExpenses() {
        return totalStandardMethodExpenses;
    }

    public String getTotalStandardMethodRevenue() {
        return totalStandardMethodRevenue;
    }

    public String getTotalMiles() {
        return totalMiles;
    }

    public String getTotalUncategorizedMiles() {
        return totalUncategorizedMiles;
    }

    public String getTotalBusinessMiles() {
        return totalBusinessMiles;
    }

    public String getTotalPersonalMiles() {
        return totalPersonalMiles;
    }

    public String getTotalMedicalMiles() {
        return totalMedicalMiles;
    }

    public String getTotalCharityMiles() {
        return totalCharityMiles;
    }

    public String getTotalKms() {
        return totalKms;
    }

    public String getTotalUncategorizedKms() {
        return totalUncategorizedKms;
    }

    public String getTotalBusinessKms() {
        return totalBusinessKms;
    }

    public String getTotalPersonalKms() {
        return totalPersonalKms;
    }

    public String getTotalMedicalKms() {
        return totalMedicalKms;
    }

    public String getTotalCharityKms() {
        return totalCharityKms;
    }

    public List<String> getUniqueUncategorizedTripDates() {
        return uniqueUncategorizedTripDates;
    }

    public List<String> getUniqueTripDates() {
        return uniqueTripDates;
    }

    public List<String> getUniqueTransactionDates() {
        return uniqueTransactionDates;
    }

    public Boolean getIsLastUncategorizedTripsPage() {
        return isLastUncategorizedTripsPage;
    }

    public Boolean getIsLastTripsPage() {
        return isLastTripsPage;
    }

    public Boolean getIsLastTransactionsPage() {
        return isLastTransactionsPage;
    }

    public String getReferralCodeName() {
        return referralCodeName;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public List<Trip> getUncategorizedTrips() {
        return uncategorizedTrips;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCustomMileageRate(Float customMileageRate) {
        this.customMileageRate = customMileageRate;
    }
}
