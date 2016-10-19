package mobile.ikreg.com.mytestapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DatePopup implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText _editText;
    private int _day;
    private int _month;
    private int _birthYear;
    private Context _context;

    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US);

    public DatePopup(Context context, EditText editText) {

        Activity activity = (Activity)context;
        this._editText = editText;
        this._editText.setOnClickListener(this);
        this._context = context;

        _editText.setText(dateFormat.format(calendar.getTime()));
    }

    public DatePopup(Context context, EditText editText, LinearLayout layout) {
        Activity activity = (Activity)context;
        this._editText = editText;
        layout.setOnClickListener(this);
        this._editText.setOnClickListener(this);
        this._context = context;

        _editText.setHint(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _birthYear = year;
        _month = monthOfYear;
        _day = dayOfMonth;

        updateDisplay(parseIntToDate(_birthYear, _month, _day));
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog dialog = new DatePickerDialog(_context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void updateDisplay(Date selectedDate) {

        _editText.setText(dateFormat.format(selectedDate));
       // _editText.setText(new StringBuilder().append(getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK))).append(", ").append(_month + "-").append(_day + "-").append(_birthYear));
    }

    public Date parseIntToDate(int year, int month, int day) {
        Date date = new GregorianCalendar(year, month, day).getTime();
        return date;
    }
}
