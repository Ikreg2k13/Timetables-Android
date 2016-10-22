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
import android.widget.LinearLayout;
import android.widget.Spinner;

import mobile.ikreg.com.mytestapplication.database.MemoryDataSource;
import mobile.ikreg.com.mytestapplication.util.ParseHelper;

public class LayoutActivity extends AppCompatActivity {

    MemoryDataSource dataSource = new MemoryDataSource(this);
    public static final String LOG_TAG = LayoutActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        DatePopup setDate = new DatePopup(LayoutActivity.this, (EditText)findViewById(R.id.editDate), (LinearLayout)findViewById(R.id.layoutDate));
        TimePopup setTime = new TimePopup(LayoutActivity.this, (EditText)findViewById(R.id.editTime), (LinearLayout)findViewById(R.id.layoutTime));

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
                long  roomLong = Long.parseLong(editRoom.getText().toString());
                long  lengthLong = Long.parseLong(editLength.getText().toString());

                if(TextUtils.isEmpty(dateString)) {
                    editDate.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(timeString)) {
                    editTime.setError("Required");
                    return;
                }

                long dateLong = ParseHelper.parseDateStringToLong(dateString);

                dataSource.createShoppingMemo(dateLong, timeString, courseString, roomLong, lengthLong, null, null);

                Intent intent = new Intent(LayoutActivity.this, Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LayoutActivity.this, Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setSpinnerItems() {
        Spinner courseSpinner = (Spinner)findViewById(R.id.spinnerCourse);
        String[] testArray = {"English", "German", "Maths", "Politics"};
        ArrayAdapter<String> testAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, testArray);
        testAdapter.setDropDownViewResource(R.layout.spinner_layout);
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
