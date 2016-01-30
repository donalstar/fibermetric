package com.guggiemedia.fibermetric.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by donal on 10/6/15.
 */

public class TimeUtils {
    public final static long ONE_SECOND = 1000;
    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long ONE_DAY = ONE_HOUR * 24;

    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

    private TimeUtils() {
    }

    /**
     * converts time (in milliseconds) to human-readable format
     * "<w> days, <x> hours, <y> minutes and (z) seconds"
     */
    public static String getDisplayableDateTime(Date date) {
        long timeStamp = date.getTime();

        long duration = (new Date()).getTime() - timeStamp;
        duration = (duration < 0) ? 0 : duration;

        StringBuffer res = new StringBuffer();

        long temp;

        if (duration >= ONE_SECOND) {
            if (duration >= ONE_DAY) {
                return DATE_FORMATTER.format(new Date(timeStamp));
            } else {
                temp = duration / ONE_HOUR;
                if (temp > 0) {
                    duration -= temp * ONE_HOUR;
                    res.append(temp).append(" hr").append(temp > 1 ? "s" : "")
                            .append(duration >= ONE_MINUTE ? ", " : "");
                }

                temp = duration / ONE_MINUTE;
                if (temp > 0) {
                    duration -= temp * ONE_MINUTE;
                    res.append(temp).append(" min").append(temp > 1 ? "s" : "");
                }

                if (!res.toString().equals("") && duration >= ONE_SECOND) {
                    res.append(" and ");
                }

                temp = duration / ONE_SECOND;
                if (temp > 0) {
                    res.append(temp).append(" sec").append(temp > 1 ? "s" : "");
                }
                return res.toString();
            }


        } else {
            return "0 second";
        }
    }


    public static void main(String args[]) {
        System.out.println(getDisplayableDateTime(get2MinsAgo()));
        System.out.println(getDisplayableDateTime(get3HoursAgo()));
        System.out.println(getDisplayableDateTime(get8Hours10MinsAgo()));
        System.out.println(getDisplayableDateTime(get1DayAgo()));
        System.out.println(getDisplayableDateTime(get6DaysAgo()));
    }

    private static Date get2MinsAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -2);

        return calendar.getTime();
    }

    private static Date get3HoursAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -3);

        return calendar.getTime();
    }

    private static Date get8Hours10MinsAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -8);
        calendar.add(Calendar.MINUTE, -10);

        return calendar.getTime();
    }

    private static Date get1DayAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

    private static Date get6DaysAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);

        return calendar.getTime();
    }
}