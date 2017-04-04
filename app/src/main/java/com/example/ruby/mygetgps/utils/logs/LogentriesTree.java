package com.example.ruby.mygetgps.utils.logs;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import com.logentries.logger.AndroidLogger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

/**
 * Integrates Logentries and Timber so every log in Timber will be published in Logentries
 */
public class LogentriesTree extends Timber.Tree {

    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
    private static final int CALL_STACK_INDEX = 7;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    private final int minLogPriority;

    private String userId = null;
    private String userEmail = null;
    private String tripId = null;

    private AndroidLogger logger = null;

    /**
     * Uses INFO as default log level.
     */
    public LogentriesTree(Context context) throws IOException {
        this(context, Log.DEBUG);
    }

    /**
     * @param logPriority Minimum log priority (as in {@link Log}).
     */
    public LogentriesTree(Context context, int logPriority) throws IOException {
        minLogPriority = logPriority;
        logger = Logentry.getInstance(context);
    }

    /**
     * Checks if event is loggable (based on minLogPriority)
     */
    @Override
    protected boolean isLoggable(int priority) {
        return priority >= minLogPriority;
    }

    /**
     * Logs messages (Not used but required)
     */
    @Override
    protected void log(int priority, String tag, String message, @Nullable Throwable t) {
        logger.log(formatMessage(priority, tag, message));
    }

    /**
     * Extract the tag which should be used for the message from the {@code element}. By default
     * this will use the class name without any anonymous class suffixes (e.g., {@code Foo$1}
     * becomes {@code Foo}).
     */
    protected String createStackElementTag(StackTraceElement element) {
        String tag = element.getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        return tag.substring(tag.lastIndexOf('.') + 1);
    }

    final String getTag(String tag) {
        if (tag != null) {
            return tag;
        }

        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= CALL_STACK_INDEX) {
            throw new IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements: are you using proguard?");
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
    }

    public String formatMessage(int priority, String tag, String message) {
        String m;
        m = "Date='" + dateFormat.format(new Date()) + "'" +
                " Level=" + formatPriority(priority) +
                " Tag='" + getTag(tag) + "'" +
                //" Version='" + BuildConfig.VERSION_NAME + "-" + BuildConfig.VERSION_CODE + "-" + BuildConfig.BUILD_TYPE + "'" +
                //" Flavor='" + BuildConfig.FLAVOR + "'" +
                " Model='" + Build.MANUFACTURER + "-" + Build.MODEL + "'" +
                " OS='" + Build.VERSION.RELEASE + "'";

        if (this.userId != null) {
            m += " user.id=" + this.userId;
        }

        if (this.userEmail != null) {
            m += " user.email=" + this.userEmail;
        }

        if (this.tripId != null) {
            m += " trip.id=" + this.tripId;
        }

        m += " " + message;

        return m;
    }

    public static String formatPriority(int priority) {
        switch (priority) {
            case Log.VERBOSE:
                return "VERBOSE";
            case Log.DEBUG:
                return "DEBUG";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.ERROR:
                return "ERROR";
            case Log.ASSERT:
                return "ASSERT";
        }
        return "OTHER";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void clearUserId() {
        this.userId = null;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void clearTripId() {
        this.tripId = null;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void clearUserEmail() {
        userEmail = null;
    }
}
