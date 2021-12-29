package com.java.project3.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date convertLocalDateToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertLocalDatetimeToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Calendar convertDateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar convertLocalDatetimeToCalendar(LocalDateTime localDateTime){
        Date date = convertLocalDatetimeToDate(localDateTime);
        return convertDateToCalendar(date);
    }
}
