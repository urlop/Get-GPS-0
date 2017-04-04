package com.example.ruby.mygetgps.utils.logs;

import android.content.Context;

import com.example.ruby.mygetgps.utils.ConfigurationConstants;
import com.logentries.logger.AndroidLogger;

import java.io.File;
import java.io.IOException;

public class Logentry {

    private static AndroidLogger instance;

    public static AndroidLogger getInstance(Context context) throws IOException {
        if(instance == null)
        {
            initializeLogFile(context);
            instance = AndroidLogger.createInstance(context, false, false, false, null, 0, ConfigurationConstants.LOGENTRIES_TOKEN, true);
        }
        return instance;
    }

    // Workaround for https://github.com/logentries/le_android/issues/38
    public static void initializeLogFile (Context context) throws IOException {
        File logFile = new File(context.getFilesDir(), "LogentriesLogStorage.log");
        if(!logFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            logFile.createNewFile();
        }
    }
}
