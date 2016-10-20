package mobile.ikreg.com.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

import mobile.ikreg.com.mytestapplication.Database.ExamMemory;
import mobile.ikreg.com.mytestapplication.Database.MemoryDataSource;

public class Main2Activity extends AppCompatActivity {

    private MemoryDataSource dataSource = new MemoryDataSource(this);
    public static final String LOG_TAG = Main2Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Log.i(LOG_TAG, "Die Datenquelle wird geöffnet.");
        //dataSource.open();

        //ExamMemory shoppingMemo = dataSource.createShoppingMemo("Testprodukt", "2");
        //Log.i(LOG_TAG, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        //Log.i(LOG_TAG, "ID: " + shoppingMemo.getId() + ", Inhalt: " + shoppingMemo.toString());

        //Log.i(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        //showAllListEntries();

        //Log.i(LOG_TAG, "Die Datenquelle wird geschlossen.");
        //dataSource.close();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this, LayoutActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();
        showAllListEntries();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

    private void showAllListEntries () {
        List<ExamMemory> examMemoList = dataSource.getAllShoppingMemos();

        ArrayAdapter<ExamMemory> examArrayAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_multiple_choice, examMemoList);

        ListView listViewExams = (ListView) findViewById(R.id.list_view);
        listViewExams.setAdapter(examArrayAdapter);
    }

}
