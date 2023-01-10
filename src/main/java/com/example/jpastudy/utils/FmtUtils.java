package com.example.jpastudy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FmtUtils {
    public static Date fmtYyyyMmDdDate(Date day) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);

        return fmt.parse(fmt.format(day));
    }

    public static String fmtYyyyMmDdStr(Date day) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);

        return fmt.format(day);
    }

    public static Date setDayDate(Date day) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.DATE, 1);

        return fmtYyyyMmDdDate(calendar.getTime());
    }

    public static String setDayStr(Date day) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.DATE, 1);

        return fmtYyyyMmDdStr(calendar.getTime());
    }
}
