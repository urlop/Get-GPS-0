package com.example.ruby.mygetgps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class APIError {

    @SerializedName("email")
    @Expose
    private List<String> email = new ArrayList<>();

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("error")
    @Expose
    private String error;

    public APIError() {
    }

    /**
     * @return The email
     */
    public List<String> getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(List<String> email) {
        this.email = email;
    }

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}
