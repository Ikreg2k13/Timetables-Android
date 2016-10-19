package mobile.ikreg.com.mytestapplication;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimePopup implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    EditText _editText;
    private int _minute;
    private int _hour;
    private Context _context;

    Calendar currentTime = Calendar.getInstance(TimeZone.getDefault());
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    public TimePopup(Context context, EditText editText) {
        Activity activity = (Activity)context;
        this._editText = editText;
        this._editText.setOnClickListener(this);
        this._context = context;

        _editText.setText(dateFormat.format(currentTime.getTime()));
    }

    public TimePopup(Context context, EditText editText, LinearLayout layout) {
        Activity activity = (Activity)context;
        this._editText = editText;
        layout.setOnClickListener(this);
        this._editText.setOnClickListener(this);
        this._context = context;

        _editText.setHint(dateFormat.format(currentTime.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
        _hour = selectedHour;
        _minute = selectedMinute;

        updateDisplay(parseIntToTime(_hour, _minute));
    }

    @Override
    public void onClick(View v) {
        TimePickerDialog dialog = new TimePickerDialog(_context, this, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false);
        dialog.show();
    }

    private void updateDisplay(Date selectedTime) {

        _editText.setText(dateFormat.format(selectedTime));

/*
        if(_minute != 0 && _hour >= 10)
            _editText.setText(new StringBuilder().append(_hour).append(":").append(_minute));
        else if(_minute != 0 && _hour <= 10)
            _editText.setText(new StringBuilder().append("0").append(_hour).append(":").append(_minute));
        else if(_minute == 0 && _hour <= 10)
            _editText.setText(new StringBuilder().append("0").append(_hour).append(":").append(_minute).append("0"));
        else if(_minute == 0 && _hour >= 10)
            _editText.setText(new StringBuilder().append(_hour).append(":").append(_minute).append("0"));
*/
    }

    public Date parseIntToTime(int hour, int minute) {
        Date date = new GregorianCalendar(1, 1, 1, hour, minute).getTime();
        return date;
    }
}
