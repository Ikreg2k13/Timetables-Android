package mobile.ikreg.com.mytestapplication.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.lang.Integer;

import mobile.ikreg.com.mytestapplication.database.ExamMemory;

public class ParseHelper {

    public ParseHelper() {

    }

    public static String parseLongToString(int parsedStringLength, long longToParse) {
        String parsedString = Long.toString(longToParse);
        String s = "";
        int nullCount = parsedStringLength - parsedString.length();

        for(int i = 0; i < nullCount; i++) {
            s = s + "0";
        }
        return s + parsedString;
    }

    public static long parseDateStringToLong(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US);
            Date date = sdf.parse(dateString);

            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static String parseLongDateToString(long dateLong) {

        return new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US).format(new Date(dateLong));
    }

    public static long parseTimeStringToLong(String timeString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
            Date date = sdf.parse(timeString);

            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String parseTimeLongToString(long timeLong) {

        return new SimpleDateFormat("hh:mm a", Locale.US).format(new Date(timeLong));
    }

    public static boolean getExpirationBool(ExamMemory exam) {
        Long e = exam.getExpired();
        Integer i = e.intValue();
        boolean b = false;
        switch(i) {
            case 0: b = false;
            break;
            case 1: b = true;
            break;
        }

        return b;
    }
}
