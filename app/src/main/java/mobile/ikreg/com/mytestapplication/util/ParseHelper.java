package mobile.ikreg.com.mytestapplication.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

            long parsedLong = date.getTime();
            return parsedLong;

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static String parseLongDateToString(long dateLong) {
        String dateString = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US).format(new Date(dateLong));

        return dateString;
    }

    public static long parseTimeStringToLong(String timeString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
            Date date = sdf.parse(timeString);

            long parsedLong = date.getTime();
            return parsedLong;

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String parseTimeLongToString(long timeLong) {
        String timeString = new SimpleDateFormat("hh:mm a", Locale.US).format(new Date(timeLong));

        return timeString;
    }
}
