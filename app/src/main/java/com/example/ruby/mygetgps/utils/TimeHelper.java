package com.example.ruby.mygetgps.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class TimeHelper {

    /**
     * Formats time to String "yyyy-MM-dd HH:mm:ss"
     * @param time  long which represents time milliseconds
     * @return string of time in format "yyyy-MM-dd HH:mm:ss"
     */
    public static String longToTimeFormat(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Timber.d("method=longToTimeFormat date='%s'", formatter.format(date));
        return formatter.format(date);
    }

    /**
     * Converts String of time into just hours in format "h:mm a"
     * @param time  string of time in format "yyyy-MM-dd HH:mm:ss"
     * @return string of hours in format "h:mm a"
     */
    public static String dateTo12TimeFormat(String time) {
        if (time != null) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(time);
                return new SimpleDateFormat("h:mm a", Locale.US).format(date);
            } catch (ParseException e) {
                Timber.e(e, "method=dateTo12TimeFormat error=Cannot format date yyyy-MM-dd HH:mm:ss");
            }

            try {
                Date date = new SimpleDateFormat("MMM d, yyyy", Locale.US).parse(time);
                return new SimpleDateFormat("h:mm a", Locale.US).format(date);
            } catch (ParseException e) {
                Timber.e(e, "method=dateTo12TimeFormat error=Cannot format date MMM d, yyyy");
            }
        }
        return "";
    }

    /**
     * Converts String of time into just the date in format "MMM d, yyyy"
     * @param dateTime  string of time in format "yyyy-MM-dd HH:mm:ss"
     * @return string of date in format "MMM d, yyyy"
     */
    public static Date dateFormattedToDate(String dateTime){
        try {
            return new SimpleDateFormat("MMM d, yyyy", Locale.US).parse(dateTime);
        } catch (ParseException e) {
            Timber.e(e, "method=dateFormattedToDate error=Cannot parse date");
            return new Date();
        }
    }

    /**
     * Converts Calendar of time into just the date in format "MMM d, yyyy"
     * @param calendar  Calendar of time
     * @return string of date in format "MMM d, yyyy"
     */
    public static String dateToString(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        return sdf.format(calendar.getTime());
    }

    /**
     * Returns a Calendar without hours, minutes, seconds or milliseconds
     * @param calendar  Calendar with full time
     * @return Calendar with just day, month and year
     */
    public static Calendar calendarTimeWithoutHours(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Gets minutes between two times
     * @param timeStart string of time when trip started in format "yyyy-MM-dd HH:mm:ss"
     * @param timeEnd   string of time when trip finished in format "yyyy-MM-dd HH:mm:ss"
     * @return  integer of minutes between each time
     */
    public static int getTimeBetween(String timeStart, String timeEnd) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Calendar cStart = Calendar.getInstance();
            cStart.setTime(format.parse(timeStart));
            Calendar cEnd = Calendar.getInstance();
            cEnd.setTime(format.parse(timeEnd));
            cStart.set(Calendar.SECOND, 0);
            cStart.set(Calendar.MILLISECOND, 0);
            cEnd.set(Calendar.SECOND, 0);
            cEnd.set(Calendar.MILLISECOND, 0);
            long diff = cEnd.getTimeInMillis() - cStart.getTimeInMillis();

            long diffMinutes = diff / (60 * 1000) % 60;
            return (int) diffMinutes;
        } catch (Exception e) {
            Timber.e(e, "method=getTimeBetween error=Problems with time computations");
            return 0;
        }
    }

    /**
     * Gets Month's name in the correct format
     * @param timeFormatted Trip's date formatted
     * @return Month of the date of the trip
     */
    public static String getMonth(String timeFormatted){
        DateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        Calendar cal  = Calendar.getInstance();
        try {
            cal.setTime(df.parse(timeFormatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM", Locale.US);
        return formatter.format(cal.getTime());
    }

    /**
     * Gets Number of day
     * @param timeFormatted Trip's date formatted
     * @return Day of the trip's date
     */
    public static String getDay(String timeFormatted){
        DateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        Calendar cal  = Calendar.getInstance();
        try {
            cal.setTime(df.parse(timeFormatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd", Locale.US);
        return formatter.format(cal.getTime());
    }
}
