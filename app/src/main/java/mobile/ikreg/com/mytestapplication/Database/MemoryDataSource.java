package mobile.ikreg.com.mytestapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class MemoryDataSource {

    private static final String LOG_TAG = MemoryDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private MemoryHelper dbHelper;
    private String[] columns = {MemoryHelper.COLUMN_ID, MemoryHelper.COLUMN_DATE, MemoryHelper.COLUMN_TIME,
                                MemoryHelper.COLUMN_COURSE, MemoryHelper.COLUMN_ROOM, MemoryHelper.COLUMN_LENGTH,
                                MemoryHelper.COLUMN_NOTIFIC, MemoryHelper.COLUMN_NOTES};


    public MemoryDataSource(Context context) {
        Log.i(LOG_TAG, "Die DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new MemoryHelper(context);
    }

    public void open() {
        Log.i(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.i(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.i(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public ExamMemory createShoppingMemo(long date, String time, String course, long room, long length, String notific, String notes) {
        ContentValues values = new ContentValues();
        values.put(MemoryHelper.COLUMN_DATE, date);
        values.put(MemoryHelper.COLUMN_TIME, time);
        values.put(MemoryHelper.COLUMN_COURSE, course);
        values.put(MemoryHelper.COLUMN_ROOM, room);
        values.put(MemoryHelper.COLUMN_LENGTH, length);
        values.put(MemoryHelper.COLUMN_NOTIFIC, notific);
        values.put(MemoryHelper.COLUMN_NOTES, notes);

        long insertId = database.insert(MemoryHelper.TABLE_EXAM_LIST, null, values);

        Cursor cursor = database.query(MemoryHelper.TABLE_EXAM_LIST,
                columns, MemoryHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        ExamMemory examMemo = cursorToExamMemo(cursor);
        cursor.close();

        return examMemo;
    }

    private ExamMemory cursorToExamMemo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(MemoryHelper.COLUMN_ID);
        int idDate = cursor.getColumnIndex(MemoryHelper.COLUMN_DATE);
        int idTime = cursor.getColumnIndex(MemoryHelper.COLUMN_TIME);
        int idCourse = cursor.getColumnIndex(MemoryHelper.COLUMN_COURSE);
        int idRoom = cursor.getColumnIndex(MemoryHelper.COLUMN_ROOM);
        int idLength = cursor.getColumnIndex(MemoryHelper.COLUMN_LENGTH);
        int idNotific = cursor.getColumnIndex(MemoryHelper.COLUMN_NOTIFIC);
        int idNotes = cursor.getColumnIndex(MemoryHelper.COLUMN_NOTES);

        long date = cursor.getLong(idDate);
        String time = cursor.getString(idTime);
        String course = cursor.getString(idCourse);
        long room = cursor.getLong(idRoom);
        long length = cursor.getLong(idLength);
        String notific = cursor.getString(idNotific);
        String notes = cursor.getString(idNotes);
        long id = cursor.getLong(idIndex);

        ExamMemory shoppingMemo = new ExamMemory(date, time, course, room, length, notific, notes, id);

        return shoppingMemo;
    }

    public List<ExamMemory> getAllShoppingMemos() {
        List<ExamMemory> examMemoList = new ArrayList<>();

        Cursor cursor = database.query(MemoryHelper.TABLE_EXAM_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        ExamMemory examMemo;

        while(!cursor.isAfterLast()) {
            examMemo = cursorToExamMemo(cursor);
            examMemoList.add(examMemo);
            Log.i(LOG_TAG, "ID: " + examMemo.getId() + ", Inhalt: " + examMemo.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return examMemoList;
    }
}
