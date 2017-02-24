package com.example.ruby.mygetgps.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpUser {

    @SerializedName("user")
    @Expose
    private User user;

    public SignUpUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
