package mobile.ikreg.com.mytestapplication.Database;

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
    private String[] columns = {MemoryHelper.COLUMN_ID, MemoryHelper.COLUMN_PRODUCT, MemoryHelper.COLUMN_QUANTITY};


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

    public ExamMemory createShoppingMemo(String product, String quantity) {
        ContentValues values = new ContentValues();
        values.put(MemoryHelper.COLUMN_PRODUCT, product);
        values.put(MemoryHelper.COLUMN_QUANTITY, quantity);

        long insertId = database.insert(MemoryHelper.TABLE_SHOPPING_LIST, null, values);

        Cursor cursor = database.query(MemoryHelper.TABLE_SHOPPING_LIST,
                columns, MemoryHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        ExamMemory shoppingMemo = cursorToShoppingMemo(cursor);
        cursor.close();

        return shoppingMemo;
    }

    private ExamMemory cursorToShoppingMemo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(MemoryHelper.COLUMN_ID);
        int idProduct = cursor.getColumnIndex(MemoryHelper.COLUMN_PRODUCT);
        int idQuantity = cursor.getColumnIndex(MemoryHelper.COLUMN_QUANTITY);

        String product = cursor.getString(idProduct);
        String quantity = cursor.getString(idQuantity);
        long id = cursor.getLong(idIndex);

        ExamMemory shoppingMemo = new ExamMemory(product, quantity, id);

        return shoppingMemo;
    }

    public List<ExamMemory> getAllShoppingMemos() {
        List<ExamMemory> examMemoList = new ArrayList<>();

        Cursor cursor = database.query(MemoryHelper.TABLE_SHOPPING_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        ExamMemory shoppingMemo;

        while(!cursor.isAfterLast()) {
            shoppingMemo = cursorToShoppingMemo(cursor);
            examMemoList.add(shoppingMemo);
            Log.i(LOG_TAG, "ID: " + shoppingMemo.getId() + ", Inhalt: " + shoppingMemo.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return examMemoList;
    }
}
