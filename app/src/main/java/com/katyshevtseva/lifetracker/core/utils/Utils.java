package com.katyshevtseva.lifetracker.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Utils {
    public static final DateFormat READABLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final DateFormat READABLE_DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static final DateFormat READABLE_TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
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

    public static Date addDays(Date date, int numOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numOfDays);
        return calendar.getTime();
    }

    public static long getUnixTimeByDate(Date date) {
        return date.getTime() / 1000;
    }

    public static Date getDateByUnixTime(long time) {
        return new Date(time * 1000);
    }
}
