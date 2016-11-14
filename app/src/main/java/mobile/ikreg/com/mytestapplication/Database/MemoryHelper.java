package mobile.ikreg.com.mytestapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MemoryHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = MemoryHelper.class.getSimpleName();
    public static final String DB_NAME = "data_memory.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_EXAM_LIST = "exam_memory";
    public static final String TABLE_COURSE_LIST = "course_memory";

    public static final String KEY_ID = "_id";

    //ExamMemory Table
    public static final String EXAM_DATE = "date";
    public static final String EXAM_TIME = "time";
    //public static final String EXAM_COURSE = "course";
    public static final String EXAM_COURSE_ID = "course_id";
    public static final String EXAM_ROOM = "room";
    public static final String EXAM_LENGTH = "length";
    public static final String EXAM_NOTIFIC = "notific";
    public static final String EXAM_NOTES = "notes";
    public static final String EXAM_EXPIRED = "expired";

    //CourseMemory Table
    public static final String COURSE_NAME="course_name";
    public static final String COURSE_COLOR="course_color";

    public static final String CREATE_TABLE_EXAM = "CREATE TABLE " + TABLE_EXAM_LIST + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EXAM_DATE + " INTEGER NOT NULL, " +
            EXAM_TIME + " TEXT NOT NULL, " +
            EXAM_COURSE_ID + " INTEGER NOT NULL, " +
            EXAM_ROOM + " INTEGER NOT NULL, " +
            EXAM_LENGTH + " INTEGER NOT NULL, " +
            EXAM_NOTIFIC + " TEXT , " +
            EXAM_NOTES + " TEXT , " +
            EXAM_EXPIRED + " INTEGER " + ")";

    public static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSE_LIST + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COURSE_NAME + " TEXT , " +
            COURSE_COLOR + " INTEGER NOT NULL " + ")";


    public MemoryHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.i(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + CREATE_TABLE_EXAM + " angelegt.");
            db.execSQL(CREATE_TABLE_EXAM);
            db.execSQL(CREATE_TABLE_COURSE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Database Error: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
