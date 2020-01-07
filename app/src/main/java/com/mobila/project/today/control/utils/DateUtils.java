package com.mobila.project.today.control.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final String DAY_DATE_FORMAT = "d.M.yyyy";

    public static final String DAY_WEEKDAY_FORMAT = "EEEE";

    public static Date parseStringToDate(String dateString) throws ParseException {
        java.text.SimpleDateFormat dateFormat =
                new SimpleDateFormat(DAY_DATE_FORMAT, Locale.getDefault());
        return dateFormat.parse(dateString);
    }
}
