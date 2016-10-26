package mobile.ikreg.com.mytestapplication.util;


import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import mobile.ikreg.com.mytestapplication.database.ExamDataSource;
import mobile.ikreg.com.mytestapplication.database.ExamMemory;

public class DateHelper {

    private static Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

    public DateHelper() {

    }

    public static String getDaysLeft(long currentDate, long goalDate) {
        long timeLeft = goalDate - currentDate;
        timeLeft = timeLeft / (1000*60*60*24);

        if(timeLeft > 14) {
            return " - in " + timeLeft / 7 + " weeks";
        }
        else if (!DateUtils.isToday(goalDate) && timeLeft <= 0 )  return " - tomorrow";
        else if (DateUtils.isToday(goalDate)) return " - today";
        else return " - in " + timeLeft + " days";
    }

    public static void deleteDbDateExpired(List<ExamMemory> examList, ExamDataSource dataSource) {
        Iterator<ExamMemory> iter = examList.iterator();

        while(iter.hasNext()) {
            ExamMemory e = iter.next();

            if(!DateUtils.isToday(e.getDate()) && e.getDate() < calendar.getTimeInMillis()) {
                dataSource.deleteExam(e);
                iter.remove();
            }
        }
    }
}
