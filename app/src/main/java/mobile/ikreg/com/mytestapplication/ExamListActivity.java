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

import mobile.ikreg.com.mytestapplication.database.CourseDataSource;
import mobile.ikreg.com.mytestapplication.database.CourseMemory;
import mobile.ikreg.com.mytestapplication.database.ExamMemory;
import mobile.ikreg.com.mytestapplication.database.ExamDataSource;
import mobile.ikreg.com.mytestapplication.util.DateHelper;
import mobile.ikreg.com.mytestapplication.util.ExamListAdapter;
import mobile.ikreg.com.mytestapplication.util.ParseHelper;

public class ExamListActivity extends AppCompatActivity {

    private ExamDataSource examSource = new ExamDataSource(this);
    private CourseDataSource courseSource = new CourseDataSource(this);
    public static final String LOG_TAG = ExamListActivity.class.getSimpleName();
    private List<ExamMemory> examList;
    private ExamListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        examSource.open();
        courseSource.open();
        Log.i(LOG_TAG, "ExamSource and CourseSource opened.");

        this.examList = getExamList();
        this.adapter = new ExamListAdapter(this, R.layout.adapter_examlist_two, examList);
        this.listView = (ListView) findViewById(R.id.list_view);
        showListEntries();
        List<CourseMemory> testList = courseSource.getActiveCourseMemos();

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                final View view1 = view;
                final ImageButton delete = (ImageButton)view1.findViewById(R.id.delete_button);
                final ExamMemory clickedExam = examList.get(pos);
                int openItemId = getOpenItemId(examList);
                View openView = listView.getChildAt(openItemId);

                if(delete.getVisibility() == View.INVISIBLE) {
                    openAnimation(view1);
                    if(openItemId != -1) {
                        closeAnimation(openView);
                        if(getOpenItem(examList) != null) {
                            getOpenItem(examList).setClosed();
                        }
                    }
                    clickedExam.setOpen();
                }
                else {
                    closeAnimation(view1);
                    clickedExam.setClosed();
                }

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(ExamListActivity.this);
                        deleteDialog.setMessage("Do you really want to delete this item?").setCancelable(false).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                examSource.deleteExam(clickedExam);
                                updateExamList(listView);
                                dialog.cancel();

                            }
                        }).setNegativeButton("Keep", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                closeAnimation(view1);
                                if(getOpenItem(examList) != null) {
                                    getOpenItem(examList).setClosed();
                                }
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

    private int getOpenItemId(List<ExamMemory> list) {
        for(ExamMemory exam : list) {
            if(exam.isOpen()) return list.indexOf(exam);
        }
        return -1;
    }

    private ExamMemory getOpenItem(List<ExamMemory> list) {
        for(ExamMemory exam : list) {
            if(exam.isOpen()) return exam;
        }
        return null;
    }

    private void openAnimation(View view) {
        final ImageButton delete = (ImageButton) view.findViewById(R.id.delete_button);
            ObjectAnimator translateList = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -150f);
            translateList.setDuration(120);
            translateList.setInterpolator(new AccelerateDecelerateInterpolator());
            translateList.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    delete.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }
            });
            translateList.start();
    }

    private void closeAnimation(View view) {
        final ImageButton delete = (ImageButton) view.findViewById(R.id.delete_button);
        ObjectAnimator translateList = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f);
        translateList.setDuration(120);
        translateList.setInterpolator(new AccelerateDecelerateInterpolator());
        translateList.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                delete.setVisibility(View.INVISIBLE);
            }
        });
        translateList.start();
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

            builder.setMessage(date.getCourse() + " exam from " + ParseHelper.parseLongDateToString(date.getDate()) + " has expired. Do you want to delete it?").setCancelable(false
            ).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    examSource.deleteExam(date);
                    updateExamList(listView);
                    dialog.cancel();
                }
            }).setNegativeButton("Keep", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    examSource.updateExpired(date, 1);
                    dialog.cancel();
                    TextView tv = (TextView)findViewById(R.id.exam_list_daysleft);
                    tv.setText(" - expired");
                }
            }).create().show();
        }
    }

    private void updateExamList(ListView listView) {
        this.examList = getExamList();
        ExamListAdapter changedAdapter = new ExamListAdapter(this, R.layout.adapter_examlist_two, examList);
        listView.setAdapter(changedAdapter);
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
