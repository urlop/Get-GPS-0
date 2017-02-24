package com.example.ruby.mygetgps.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ruby.mygetgps.models.Trip;
import com.example.ruby.mygetgps.utils.Constants;
import com.example.ruby.mygetgps.utils.TripHelper;

import timber.log.Timber;

public class StopTripReceiver extends BroadcastReceiver {
    public StopTripReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Timber.d("method=onReceive intent.action='%s'", intent.getAction());

            switch (intent.getAction()) {
                /*case UploadService.ACTION:
                    Trip trip = (Trip) intent.getSerializableExtra(Constants.TRIP_OBJECT_EXTRA);
                    TripHelper.addRecentlyPostedTrip(trip, context);
                    break;*/
            }
        }
    }
}
