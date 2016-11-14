package mobile.ikreg.com.mytestapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static mobile.ikreg.com.mytestapplication.R.id.course;

public class CourseDataSource {

    private SQLiteDatabase database;
    private MemoryHelper dbHelper;

    private String[] columns = { MemoryHelper.KEY_ID, MemoryHelper.COURSE_NAME, MemoryHelper.COURSE_COLOR };


    public CourseDataSource(Context context) {
        dbHelper = new MemoryHelper(context);
    }

    public void open() {
        database = dbHelper.getReadableDatabase();
        Log.i("Database", "Course DataBase: " + database);
    }

    public void close() {
        dbHelper.close();
    }

    public CourseMemory createCourse(String name, int color) {
        ContentValues values = new ContentValues();
        values.put(MemoryHelper.COURSE_NAME, name);
        values.put(MemoryHelper.COURSE_COLOR, color);

        long insertId = database.insert(MemoryHelper.TABLE_COURSE_LIST, null, values);

        Cursor cursor = database.query(MemoryHelper.TABLE_COURSE_LIST, columns,
                MemoryHelper.KEY_ID + "=" + insertId, null, null, null, null);
        cursor.moveToFirst();
        CourseMemory course = cursorToCourseMemo(cursor);
        cursor.close();

        return course;
    }

    private CourseMemory cursorToCourseMemo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(MemoryHelper.KEY_ID);
        int idName = cursor.getColumnIndex(MemoryHelper.COURSE_NAME);
        int idColor = cursor.getColumnIndex(MemoryHelper.COURSE_COLOR);

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(idName);
        long color = cursor.getLong(idColor);

        return new CourseMemory(name, color, id);
    }

    public List<CourseMemory> getActiveCourseMemos() {

        List<CourseMemory> courseList = new ArrayList<>();

        Cursor cursor = database.query(MemoryHelper.TABLE_COURSE_LIST, columns, null, null, null, null, null);

        cursor.moveToFirst();

        CourseMemory course;

        while(!cursor.isAfterLast()) {
            course = cursorToCourseMemo(cursor);
            courseList.add(course);
            Log.i("Database", "ID: " + course.getId() + ", Inhalt: " + course.getName());
            cursor.moveToNext();
        }
        cursor.close();
        //db.close();
        return courseList;
    }

    public void deleteCourse(CourseMemory course) {
        long id = course.getId();

        database.delete(MemoryHelper.TABLE_COURSE_LIST, MemoryHelper.KEY_ID + "=" + id, null);
    }

    public CourseMemory getCourseById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + MemoryHelper.TABLE_COURSE_LIST + " WHERE " + MemoryHelper.KEY_ID + " = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        CourseMemory course = cursorToCourseMemo(cursor);

        db.close();
        cursor.close();

        return course;
    }
}
