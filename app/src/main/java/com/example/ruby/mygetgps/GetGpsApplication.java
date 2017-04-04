package com.example.ruby.mygetgps;

import android.app.Application;

import com.example.ruby.mygetgps.utils.logs.LogentriesTree;
import com.orm.SugarContext;

import java.io.IOException;

import timber.log.Timber;

public class GetGpsApplication extends Application {

    private static GetGpsApplication sInstance;

    public static GetGpsApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    private void initializeInstance() {
        sInstance = this;
        Timber.plant(new Timber.DebugTree());
        LogentriesTree leTree = null;
        try {
            leTree = new LogentriesTree(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Timber.plant(leTree);
        SugarContext.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Timber.w("Device memory is low");
    }
}
