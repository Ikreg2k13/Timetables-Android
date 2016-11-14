package mobile.ikreg.com.mytestapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import mobile.ikreg.com.mytestapplication.database.CourseDataSource;
import mobile.ikreg.com.mytestapplication.database.CourseMemory;
import mobile.ikreg.com.mytestapplication.database.ExamDataSource;
import mobile.ikreg.com.mytestapplication.util.AddExamSpinnerAdapter;
import mobile.ikreg.com.mytestapplication.util.dialog.AddCoursePopup;
import mobile.ikreg.com.mytestapplication.util.dialog.DatePopup;
import mobile.ikreg.com.mytestapplication.util.ParseHelper;
import mobile.ikreg.com.mytestapplication.util.dialog.TimePopup;

public class ExamAddActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    ExamDataSource dataSource = new ExamDataSource(this);
    CourseDataSource courseSource = new CourseDataSource(this);
    public static final String LOG_TAG = ExamAddActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexam);

        DatePopup setDate = new DatePopup(ExamAddActivity.this, (EditText)findViewById(R.id.editDate), (LinearLayout)findViewById(R.id.layoutDate));
        TimePopup setTime = new TimePopup(ExamAddActivity.this, (EditText)findViewById(R.id.editTime), (LinearLayout)findViewById(R.id.layoutTime));

        dataSource.open();
        courseSource.open();

        setSpinnerItems();
    }

    private void onButtonPressed() {
        Button saveButton = (Button)findViewById(R.id.buttonSave);
        Button cancelButton = (Button)findViewById(R.id.buttonCancel);
        ImageView addCourse = (ImageView)findViewById(R.id.addcourse);
        final EditText editDate = (EditText)findViewById(R.id.editDate);
        final EditText editTime = (EditText)findViewById(R.id.editTime);
        final Spinner spinnerCourse = (Spinner)findViewById(R.id.spinnerCourse);
        final EditText editRoom = (EditText)findViewById(R.id.editRoom);
        final EditText editLength = (EditText)findViewById(R.id.editLength);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCoursePopup addCourse = new AddCoursePopup(ExamAddActivity.this, courseSource);
                addCourse.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = editDate.getText().toString();
                String timeString = editTime.getText().toString();
                //String course_id = spinnerCourse.getSelectedItem().toString();
                String roomString = editRoom.getText().toString();
                String lengthString = editLength.getText().toString();

                if (TextUtils.isEmpty(dateString)) {
                    editDate.setError("Required");
                    return;
/*                } else if (ParseHelper.parseDateStringToLong(editDate.getText().toString()) < calendar.getTimeInMillis() &&
                        !DateUtils.isToday(ParseHelper.parseDateStringToLong(editDate.getText().toString()))) {
                    editDate.setError("Required");
                    //Snackbar.make(view, "Date set is invalid!", Snackbar.LENGTH_LONG).setAction("Action", null);
                    //editDate.setText(ParseHelper.parseLongDateToString(calendar.getTimeInMillis()));
                    return;
*/
                    }

                if(TextUtils.isEmpty(timeString)) {
                    editTime.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(roomString)) {
                    editRoom.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(lengthString)) {
                    editLength.setError("Required");
                    return;
                }

                long course_id = spinnerCourse.getSelectedItemPosition() + 1;
                long roomLong = Long.parseLong(editRoom.getText().toString());
                long lengthLong = Long.parseLong(editLength.getText().toString());
                long dateLong = ParseHelper.parseDateStringToLong(dateString);

                dataSource.createShoppingMemo(dateLong, timeString, course_id, roomLong, lengthLong, null, null, 0);

                Intent intent = new Intent(ExamAddActivity.this, ExamListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setSpinnerItems() {
        Spinner courseSpinner = (Spinner)findViewById(R.id.spinnerCourse);
        List<CourseMemory> courseList = courseSource.getActiveCourseMemos();
        ArrayAdapter<CourseMemory> testAdapter = new AddExamSpinnerAdapter(this, R.layout.adapter_coursespinner_dropdown, courseList);
        testAdapter.setDropDownViewResource(R.layout.adapter_coursespinner_dropdown);
        courseSpinner.setAdapter(testAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(LOG_TAG, "Die Datenquelle im Add wird geöffnet.");
        //dataSource.open();
        //courseSource.open();
        onButtonPressed();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(LOG_TAG, "Die Datenquelle wird im Add geschlossen.");
        dataSource.close();
        courseSource.close();
    }
}
