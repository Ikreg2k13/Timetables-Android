package mobile.ikreg.com.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mobile.ikreg.com.mytestapplication.database.ExamMemory;
import mobile.ikreg.com.mytestapplication.database.ExamDataSource;
import mobile.ikreg.com.mytestapplication.util.ExamListAdapter;

public class ExamListActivity extends AppCompatActivity {

    private ExamDataSource dataSource = new ExamDataSource(this);
    public static final String LOG_TAG = ExamListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examlist);
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
                startActivity(new Intent(ExamListActivity.this, ExamAddActivity.class));
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

        List<ExamMemory> examMemoList = dataSource.getActiveExamMemos();

        Collections.sort(examMemoList, new Comparator<ExamMemory>() {
            @Override
            public int compare(final ExamMemory object1, final ExamMemory object2) {
                return object1.getDate() < object2.getDate() ? -1 : object1.getDate() > object2.getDate() ? 1 : 0;
            }
        });

        ExamListAdapter examArrayAdapter = new ExamListAdapter(this, R.layout.adapter_examlist, examMemoList);

        ListView listViewExams = (ListView) findViewById(R.id.list_view);
        listViewExams.setAdapter(examArrayAdapter);
    }

}
