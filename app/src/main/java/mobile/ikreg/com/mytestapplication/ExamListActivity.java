package mobile.ikreg.com.mytestapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mobile.ikreg.com.mytestapplication.database.CourseDataSource;
import mobile.ikreg.com.mytestapplication.database.ExamMemory;
import mobile.ikreg.com.mytestapplication.database.ExamDataSource;
import mobile.ikreg.com.mytestapplication.util.DateHelper;
import mobile.ikreg.com.mytestapplication.util.ExamListAdapter;
import mobile.ikreg.com.mytestapplication.util.ParseHelper;

@SuppressWarnings("serial")
public class ExamListActivity extends AppCompatActivity implements Serializable {

    private ExamDataSource examSource = new ExamDataSource(this);
    private CourseDataSource courseSource = new CourseDataSource(this);
    public static final String LOG_TAG = ExamListActivity.class.getSimpleName();
    private List<ExamMemory> examList;
    private ExamListAdapter adapter;
    private ListView listView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examlist);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        examSource.open();
        courseSource.open();
        Log.i(LOG_TAG, "ExamSource and CourseSource opened.");

        this.examList = getExamList();
        this.adapter = new ExamListAdapter(this, R.layout.adapter_examlist_two, examList);
        this.listView = (ListView) findViewById(R.id.list_view);
        listView.setLongClickable(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setEmptyView(findViewById(R.id.examlist_empty));
        showListEntries();

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                for(int i = 0; i < examList.size(); i++) {
                    if(adapter.isPositionChecked(i)) listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                adapter.clearSelection();
            }
            int nr = 0;

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if(checked) {
                    nr++;
                    adapter.setNewSelection(position, checked);
                    listView.getChildAt(position).setBackgroundColor(Color.parseColor("#E0E0E0"));
                }
                else {
                    nr--;
                    adapter.removeSelection(position);
                    listView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                }
                if(nr > 1) findViewById(R.id.examlist_cont_edit).setVisibility(View.GONE);
                else findViewById(R.id.examlist_cont_edit).setVisibility(View.VISIBLE);
                mode.setTitle(nr + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                nr = 0;
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.examlist_cont_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.examlist_cont_delete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamListActivity.this);

                        builder.setMessage("Delete " + nr + " exams?").setCancelable(true
                        ).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for(Integer key : adapter.getCurrentCheckedPosition()) {
                                    listView.getChildAt(key).setBackgroundColor(Color.TRANSPARENT);
                                    deleteExam(key);
                                }
                                dialog.cancel();
                                nr = 0;
                                adapter.clearSelection();
                                mode.finish();
                            }
                        }).setNegativeButton("Keep", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
                        break;
                    case R.id.examlist_cont_edit:
                        int pos = (int) adapter.getCurrentCheckedPosition().toArray()[0];
                        ExamMemory exam = examList.get(pos);
                        String[] data = {Long.toString(exam.getId()), Long.toString(exam.getDate()), exam.getTime(), Long.toString(exam.getCourseId()), Long.toString(exam.getRoom()), Long.toString(exam.getLength()), exam.getNotific(), exam.getNotes(), "edit"};
                        Intent intent = new Intent(ExamListActivity.this, ExamAddActivity.class);
                        intent.putExtra("editData", data);
                        startActivity(intent);

                        nr = 0;
                        listView.getChildAt(pos).setBackgroundColor(Color.TRANSPARENT);
                        adapter.clearSelection();
                        mode.finish();
                        break;
                }
                return false;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setItemChecked(position, !adapter.isPositionChecked(position));
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExamListActivity.this, ExamAddActivity.class));
            }
        });
    }

    //MenuInflator for Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exam_list_menu, menu);
        return true;
    }

    //Handle MenuSelection in Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.exam_delete:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(LOG_TAG, "Die Datenquellen werden geschlossen.");
        courseSource.close();
        examSource.close();
    }

    private void showListEntries () {
        listView.setAdapter(adapter);

        final ExamMemory date = DateHelper.getDateExpired(examList);

        if(date != null && !ParseHelper.getExpirationBool(date)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(date.getCourseId() + " exam from " + ParseHelper.parseLongDateToString(date.getDate()) + " has expired. Do you want to delete it?").setCancelable(false
            ).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    deleteExam(date);
                    dialog.cancel();
                    showListEntries();
                }
            }).setNegativeButton("Keep", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    examSource.updateExpired(date, 1);
                    dialog.cancel();
                    showListEntries();
                }
            }).create().show();
        }
    }

    private void deleteExam(ExamMemory exam) {
        examSource.deleteExam(exam);
        examList.remove(exam);
        adapter.remove(exam);
        adapter.notifyDataSetChanged();
    }

    private void deleteExam(int position) {
        ExamMemory exam = examList.get(position);
        deleteExam(exam);
    }

    private List<ExamMemory> getExamList() {
        List<ExamMemory> list = examSource.getActiveExamMemos();
        Collections.sort(list, new Comparator<ExamMemory>() {
            @Override
            public int compare(final ExamMemory object1, final ExamMemory object2) {
                return object1.getDate() < object2.getDate() ? -1 : object1.getDate() > object2.getDate() ? 1 : 0;
            }
        });
        return list;
    }

}
