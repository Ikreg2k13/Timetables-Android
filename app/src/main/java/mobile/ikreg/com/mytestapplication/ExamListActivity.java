package mobile.ikreg.com.mytestapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
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

        dataSource.open();

        final ListView listViewExams = (ListView) findViewById(R.id.list_view);
        final List<ExamMemory> examMemoList = getExamList();

        listViewExams.setClickable(true);
        listViewExams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                final int position = pos;
                final ImageButton delete = (ImageButton)view.findViewById(R.id.delete_button);
                final int itemHeight = view.getHeight();

                if(delete.getVisibility() == View.GONE) {
                    openAnimation(delete, view);
                }
                else {
                    closeAnimation(delete, view);
                }

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(ExamListActivity.this);
                        deleteDialog.setMessage("Do you really want to delete this item?").setCancelable(true).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ExamMemory deleteExam = examMemoList.get(position);
                                dataSource.deleteExam(deleteExam);
                                updateExamList(listViewExams);
                                dialog.cancel();

                            }
                        }).setNegativeButton("Keep", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
                    }
                });
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

    private void openAnimation(final ImageButton delete, View view) {
        final int itemHeight = view.getHeight();
            ObjectAnimator translateList = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -150f);
            translateList.setDuration(200);
            translateList.setInterpolator(new AccelerateDecelerateInterpolator());
            translateList.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    delete.getLayoutParams().height = itemHeight;
                    delete.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }
            });
            translateList.start();
    }

    private void closeAnimation(final ImageButton delete, View view) {
        ObjectAnimator translateList = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f);
        translateList.setDuration(200);
        translateList.setInterpolator(new AccelerateDecelerateInterpolator());
        translateList.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                delete.setVisibility(View.GONE);
            }
        });
        translateList.start();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(LOG_TAG, "Die Datenquelle wird geöffnet.");
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

            builder.setMessage(date.getCourse() + " exam from " + ParseHelper.parseLongDateToString(date.getDate()) + " has expired. Do you want to delete it?").setCancelable(true).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
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
