package mobile.ikreg.com.mytestapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mobile.ikreg.com.mytestapplication.database.ExamMemory;
import mobile.ikreg.com.mytestapplication.database.ExamDataSource;
import mobile.ikreg.com.mytestapplication.util.DateHelper;
import mobile.ikreg.com.mytestapplication.util.ExamListAdapter;
import mobile.ikreg.com.mytestapplication.util.ParseHelper;

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

        final List<ExamMemory> examMemoList = getExamList();
        final ListView listViewExams = (ListView) findViewById(R.id.list_view);

        final ExamListAdapter examArrayAdapter = new ExamListAdapter(this, R.layout.adapter_examlist, examMemoList);
        listViewExams.setAdapter(examArrayAdapter);

        final ExamMemory date = DateHelper.getDateExpired(examMemoList);

        if(date != null && !ParseHelper.getExpirationBool(date)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("An exam has expired. Do you want to delete it?").setCancelable(true).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dataSource.deleteExam(date);
                    updateExamList(listViewExams);
                    dialog.cancel();

                }
            }).setNegativeButton("Keep", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dataSource.updateExamMemory(date.getId(), date.getDate(), date.getTime(), date.getCourse(), date.getRoom(), date.getLength(), date.getNotific(), date.getNotes(), 1);
                    dialog.cancel();
                    TextView tv = (TextView)findViewById(R.id.exam_list_daysleft);
                    tv.setText(" - expired");
                }
            }).create().show();
        }
    }

    private void updateExamList(ListView listView) {
        List<ExamMemory> changedList = getExamList();
        ExamListAdapter changedAdapter = new ExamListAdapter(this, R.layout.adapter_examlist, changedList);
        listView.setAdapter(changedAdapter);
    }

    private List<ExamMemory> getExamList() {
        List<ExamMemory> list = dataSource.getActiveExamMemos();
        Collections.sort(list, new Comparator<ExamMemory>() {
            @Override
            public int compare(final ExamMemory object1, final ExamMemory object2) {
                return object1.getDate() < object2.getDate() ? -1 : object1.getDate() > object2.getDate() ? 1 : 0;
            }
        });
        return list;
    }

}
