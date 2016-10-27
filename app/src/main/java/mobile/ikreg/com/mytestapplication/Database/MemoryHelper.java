package mobile.ikreg.com.mytestapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MemoryHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = MemoryHelper.class.getSimpleName();
    public static final String DB_NAME = "data_memory.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_EXAM_LIST = "exam_memory";

    //Product equals Date
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_COURSE = "course";
    public static final String COLUMN_ROOM = "room";
    public static final String COLUMN_LENGTH = "length";
    public static final String COLUMN_NOTIFIC = "notific";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_EXPIRED = "expired";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_EXAM_LIST + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DATE + " INTEGER NOT NULL, " +
            COLUMN_TIME + " TEXT NOT NULL, " +
            COLUMN_COURSE + " TEXT NOT NULL, " +
            COLUMN_ROOM + " INTEGER NOT NULL, " +
            COLUMN_LENGTH + " INTEGER NOT NULL, " +
            COLUMN_NOTIFIC + " TEXT , " +
            COLUMN_NOTES + " TEXT , " +
            COLUMN_EXPIRED + " INTEGER " + ")";


    public MemoryHelper(Context context) {
        //super(context, "PLATZHALTER_DATENBANKNAME", null, 1);
        super(context, DB_NAME, null, DB_VERSION);
        Log.i(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Database Error: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
