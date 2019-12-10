package com.example.chris.travelorga_kth.helper;

import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final String datePattern = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'";
        public static String fromDateToString(Date date) {
            String dateString;
            dateString = new SimpleDateFormat(datePattern).format(date);
            return dateString;
        }


    public static Date fromStringToDate(String dateString) throws ParseException {
        Date date;
        date = new SimpleDateFormat(datePattern).parse(dateString);
        return date;
    }

    public static void main(String[] argv) {
        try {
            System.out.println(fromStringToDate("2019-12-08T06:03:00.000Z"));
//            System.out.println(fromDateToString(fromStringToDate("2019-12-08T06:03:00.000Z")));
        }
        catch(Exception ignored) {}
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


}
