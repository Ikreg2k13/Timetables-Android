package mobile.ikreg.com.mytestapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import mobile.ikreg.com.mytestapplication.util.DateHelper;

public class ExamDataSource {

    private static final String LOG_TAG = ExamDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private MemoryHelper dbHelper;
    private String[] columns = {MemoryHelper.KEY_ID, MemoryHelper.EXAM_DATE, MemoryHelper.EXAM_TIME,
                                MemoryHelper.EXAM_COURSE_ID, MemoryHelper.EXAM_ROOM, MemoryHelper.EXAM_LENGTH,
                                MemoryHelper.EXAM_NOTIFIC, MemoryHelper.EXAM_NOTES, MemoryHelper.EXAM_EXPIRED};


    public ExamDataSource(Context context) {
        Log.i(LOG_TAG, "Die DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new MemoryHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
        Log.i("Database", "Exam DataBase: " + database);
    }

    public void close() {
        dbHelper.close();
        Log.i(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public ExamMemory createShoppingMemo(long date, String time, long course_id, long room, long length, String notific, String notes, long expired) {
        ContentValues values = new ContentValues();
        values.put(MemoryHelper.EXAM_DATE, date);
        values.put(MemoryHelper.EXAM_TIME, time);
        values.put(MemoryHelper.EXAM_COURSE_ID, course_id);
        values.put(MemoryHelper.EXAM_ROOM, room);
        values.put(MemoryHelper.EXAM_LENGTH, length);
        values.put(MemoryHelper.EXAM_NOTIFIC, notific);
        values.put(MemoryHelper.EXAM_NOTES, notes);
        values.put(MemoryHelper.EXAM_EXPIRED, expired);

        long insertId = database.insert(MemoryHelper.TABLE_EXAM_LIST, null, values);

        Cursor cursor = database.query(MemoryHelper.TABLE_EXAM_LIST,
                columns, MemoryHelper.KEY_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        ExamMemory examMemo = cursorToExamMemo(cursor);
        cursor.close();

        return examMemo;
    }

    private ExamMemory cursorToExamMemo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(MemoryHelper.KEY_ID);
        int idDate = cursor.getColumnIndex(MemoryHelper.EXAM_DATE);
        int idTime = cursor.getColumnIndex(MemoryHelper.EXAM_TIME);
        int idCourse = cursor.getColumnIndex(MemoryHelper.EXAM_COURSE_ID);
        int idRoom = cursor.getColumnIndex(MemoryHelper.EXAM_ROOM);
        int idLength = cursor.getColumnIndex(MemoryHelper.EXAM_LENGTH);
        int idNotific = cursor.getColumnIndex(MemoryHelper.EXAM_NOTIFIC);
        int idNotes = cursor.getColumnIndex(MemoryHelper.EXAM_NOTES);
        int idExpired = cursor.getColumnIndex(MemoryHelper.EXAM_EXPIRED);

        long date = cursor.getLong(idDate);
        String time = cursor.getString(idTime);
        long course_id = cursor.getLong(idCourse);
        long room = cursor.getLong(idRoom);
        long length = cursor.getLong(idLength);
        String notific = cursor.getString(idNotific);
        String notes = cursor.getString(idNotes);
        long expired = cursor.getLong(idExpired);
        long id = cursor.getLong(idIndex);

        return new ExamMemory(date, time, course_id, room, length, notific, notes, expired, id);
    }

    public List<ExamMemory> getActiveExamMemos() {

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
        //db.close();

        return examMemoList;
    }

    public void deleteExam(ExamMemory exam) {
        long id = exam.getId();

        database.delete(MemoryHelper.TABLE_EXAM_LIST, MemoryHelper.KEY_ID + "=" + id, null);
    }

    public ExamMemory updateExamMemory(long id, long date, String time, long course_id, long room, long length, String notific, String notes, long expired) {
        ContentValues values = new ContentValues();
        values.put(MemoryHelper.EXAM_DATE, date);
        values.put(MemoryHelper.EXAM_TIME, time);
        values.put(MemoryHelper.EXAM_COURSE_ID, course_id);
        values.put(MemoryHelper.EXAM_ROOM, room);
        values.put(MemoryHelper.EXAM_LENGTH, length);
        values.put(MemoryHelper.EXAM_NOTIFIC, notific);
        values.put(MemoryHelper.EXAM_NOTES, notes);
        values.put(MemoryHelper.EXAM_EXPIRED, expired);

        database.update(MemoryHelper.TABLE_EXAM_LIST,
                values,
                MemoryHelper.KEY_ID + "=" + id,
                null);

        Cursor cursor = database.query(MemoryHelper.TABLE_EXAM_LIST,
                columns, MemoryHelper.KEY_ID + "=" + id,
                null, null, null, null);

        cursor.moveToFirst();
        ExamMemory examMemo = cursorToExamMemo(cursor);
        cursor.close();

        return examMemo;
    }

    public void updateExpired(ExamMemory exam, int expired) {
        ContentValues values = new ContentValues();
        values.put(MemoryHelper.EXAM_EXPIRED, expired);

        database.update(MemoryHelper.TABLE_EXAM_LIST, values, MemoryHelper.KEY_ID + "= ?",
                new String[] { String.valueOf(exam.getId())});
    }
}
