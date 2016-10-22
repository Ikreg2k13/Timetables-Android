package mobile.ikreg.com.mytestapplication.util;


import android.text.format.DateUtils;

public class DateHelper {

    public DateHelper() {

    }

    public static String getDaysLeft(long currentDate, long goalDate) {
        long timeLeft = goalDate - currentDate;
        timeLeft = timeLeft / (1000*60*60*24);

        if(timeLeft > 14) {
            return " - in " + timeLeft / 7 + " weeks";
        }
        else if (DateUtils.isToday(goalDate)) return " - today";
        else return " - in " + timeLeft + " days";
    }
}
