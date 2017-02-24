package com.example.ruby.mygetgps.models;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PhotoAttributes {
    RequestBody imageFile;

    public PhotoAttributes(File imageFile) {
        this.imageFile =  RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
    }

    public RequestBody getImageFile() {
        return imageFile;
    }
}
