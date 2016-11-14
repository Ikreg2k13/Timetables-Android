package mobile.ikreg.com.mytestapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mobile.ikreg.com.mytestapplication.R;
import mobile.ikreg.com.mytestapplication.database.CourseMemory;

public class AddExamSpinnerAdapter extends ArrayAdapter<CourseMemory> {

    private Context context;
    private List<CourseMemory> courseList;
    private int layoutResourceId;

    public AddExamSpinnerAdapter(Context context, int layoutResourceId, List<CourseMemory> list) {
        super(context, layoutResourceId, list);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.courseList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_coursespinner, parent, false);
        }

        TextView course = (TextView)convertView.findViewById(R.id.textView5);

        course.setText(this.courseList.get(position).getName());
        course.setTextColor((int) this.courseList.get(position).getColor());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_coursespinner_dropdown, parent, false);
        }

        TextView course = (TextView)convertView.findViewById(R.id.courseSpinnerCourse);
        View color = convertView.findViewById(R.id.courseSpinnerColor);

        course.setText(this.courseList.get(position).getName());
        color.setBackgroundColor((int) courseList.get(position).getColor());

        return convertView;
    }
}
