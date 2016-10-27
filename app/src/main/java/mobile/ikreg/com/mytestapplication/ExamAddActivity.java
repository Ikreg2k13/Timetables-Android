package mobile.ikreg.com.mytestapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.TimeZone;

import mobile.ikreg.com.mytestapplication.database.ExamDataSource;
import mobile.ikreg.com.mytestapplication.util.AddExamSpinnerAdapter;
import mobile.ikreg.com.mytestapplication.util.dialog.DatePopup;
import mobile.ikreg.com.mytestapplication.util.ParseHelper;
import mobile.ikreg.com.mytestapplication.util.dialog.TimePopup;

public class ExamAddActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    ExamDataSource dataSource = new ExamDataSource(this);
    public static final String LOG_TAG = ExamAddActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexam);

        DatePopup setDate = new DatePopup(ExamAddActivity.this, (EditText)findViewById(R.id.editDate), (LinearLayout)findViewById(R.id.layoutDate));
        TimePopup setTime = new TimePopup(ExamAddActivity.this, (EditText)findViewById(R.id.editTime), (LinearLayout)findViewById(R.id.layoutTime));

        setSpinnerItems();
    }

    private void onButtonPressed() {
        Button saveButton = (Button)findViewById(R.id.buttonSave);
        Button cancelButton = (Button)findViewById(R.id.buttonCancel);
        final EditText editDate = (EditText)findViewById(R.id.editDate);
        final EditText editTime = (EditText)findViewById(R.id.editTime);
        final Spinner spinnerCourse = (Spinner)findViewById(R.id.spinnerCourse);
        final EditText editRoom = (EditText)findViewById(R.id.editRoom);
        final EditText editLength = (EditText)findViewById(R.id.editLength);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = editDate.getText().toString();
                String timeString = editTime.getText().toString();
                String courseString = spinnerCourse.getSelectedItem().toString();
                long roomLong = Long.parseLong(editRoom.getText().toString());
                long lengthLong = Long.parseLong(editLength.getText().toString());

                if (TextUtils.isEmpty(dateString)) {
                    editDate.setError("Required");
                    return;
//                } else if (ParseHelper.parseDateStringToLong(editDate.getText().toString()) < calendar.getTimeInMillis() &&
//                        !DateUtils.isToday(ParseHelper.parseDateStringToLong(editDate.getText().toString()))) {
//                    editDate.setError("Required");
                    //Snackbar.make(view, "Date set is invalid!", Snackbar.LENGTH_LONG).setAction("Action", null);
                    //editDate.setText(ParseHelper.parseLongDateToString(calendar.getTimeInMillis()));
//                    return;
                    }

                if(TextUtils.isEmpty(timeString)) {
                    editTime.setError("Required");
                    return;
                }

                long dateLong = ParseHelper.parseDateStringToLong(dateString);

                dataSource.createShoppingMemo(dateLong, timeString, courseString, roomLong, lengthLong, null, null, 0);

                Intent intent = new Intent(ExamAddActivity.this, ExamListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExamAddActivity.this, ExamListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setSpinnerItems() {
        Spinner courseSpinner = (Spinner)findViewById(R.id.spinnerCourse);
        String[] testArray = {"English", "German", "Maths", "Politics", "Add Course"};
        ArrayAdapter<String> testAdapter = new AddExamSpinnerAdapter(this, R.layout.adapter_coursespinner, testArray);
        testAdapter.setDropDownViewResource(R.layout.adapter_coursespinner);
        courseSpinner.setAdapter(testAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(LOG_TAG, "Die Datenquelle im Add wird geöffnet.");
        dataSource.open();
        onButtonPressed();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(LOG_TAG, "Die Datenquelle wird im Add geschlossen.");
        dataSource.close();
    }
}
