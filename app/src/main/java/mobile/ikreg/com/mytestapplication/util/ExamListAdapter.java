package mobile.ikreg.com.mytestapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import mobile.ikreg.com.mytestapplication.R;
import mobile.ikreg.com.mytestapplication.database.ExamMemory;
import mobile.ikreg.com.mytestapplication.util.DateHelper;
import mobile.ikreg.com.mytestapplication.util.ParseHelper;

public class ExamListAdapter extends ArrayAdapter<ExamMemory> {

    Calendar c = Calendar.getInstance(TimeZone.getDefault());
    private Context context;
    private List<ExamMemory> examList;
    private int layoutResourceId;

    public ExamListAdapter(Context context, int layoutResourceId, List<ExamMemory> list) {
        super(context, layoutResourceId, list);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.examList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exam_list_layout, parent, false);
        }

        TextView course = (TextView)convertView.findViewById(R.id.exam_list_course);
        TextView date = (TextView)convertView.findViewById(R.id.exam_list_date);
        TextView time = (TextView)convertView.findViewById(R.id.exam_list_time);
        TextView daysleft = (TextView)convertView.findViewById(R.id.exam_list_daysleft);
        TextView room = (TextView)convertView.findViewById(R.id.exam_list_room);
        //TextView length = (TextView)convertView.findViewById(R.id.exam_list_length);
        //TextView fromlesson = (TextView)convertView.findViewById(R.id.exam_list_fromlesson);



        course.setText(this.examList.get(position).getCourse());
        date.setText(ParseHelper.parseLongDateToString(this.examList.get(position).getDate()));
        time.setText(this.examList.get(position).getTime());
        daysleft.setText(DateHelper.getDaysLeft(c.getTimeInMillis(),this.examList.get(position).getDate()));
        room.setText("R " + ParseHelper.parseLongToString(3, this.examList.get(position).getRoom()));
        //length.setText(ParseHelper.parseLongToString(3, this.examList.get(position).getLength()));
        //fromlesson.setText(this.examList.get(position).getTime());

        return convertView;
    }
}
