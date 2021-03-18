package com.delaroystudios.weatherapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getReadableDateFromLong(long dateInMillis) {

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY");
        String dateString = formatter.format(new Date(dateInMillis * 1000));

        return dateString;
    }
}
