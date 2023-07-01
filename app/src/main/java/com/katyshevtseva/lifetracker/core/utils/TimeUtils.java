package com.katyshevtseva.lifetracker.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class TimeUtils {
    public static DateFormat READABLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static Time plus(Time time1, Time time2) {
        return new Time(time1.getTotalMinutes() + time2.getTotalMinutes());
    }

    public static int minus(Time time1, Time time2) {
        return time1.getTotalMinutes() - time2.getTotalMinutes();
    }

    public static boolean afterNow(Time time) {
        return after(time, getNow());
    }

    public static Time getNow() {
        return getTime(new Date());
    }

    public static Time getTime(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return new Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public static Date setTime(Date date, Time time) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
        calendar.set(Calendar.MINUTE, time.getMinute());
        return calendar.getTime();
    }

    public static boolean after(Time time1, Time time2) {
        return minus(time1, time2) > 0;
    }

    public static boolean beforeIgnoreTime(Date date1, Date date2) {
        return removeTimeFromDate(date1).before(removeTimeFromDate(date2));
    }

    public static boolean equalsIgnoreTime(Date date1, Date date2) {
        return removeTimeFromDate(date1).equals(removeTimeFromDate(date2));
    }

    public static Date removeTimeFromDate(Date date) {
        try {
            return READABLE_DATE_FORMAT.parse(READABLE_DATE_FORMAT.format(date));
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }
}
