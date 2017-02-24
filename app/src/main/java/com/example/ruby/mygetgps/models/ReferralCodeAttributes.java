package com.example.ruby.mygetgps.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferralCodeAttributes {

    @SerializedName("name")
    @Expose
    private String name;

    public ReferralCodeAttributes() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
