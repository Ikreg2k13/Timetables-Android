package mobile.ikreg.com.mytestapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mobile.ikreg.com.mytestapplication.R;

public class AddExamSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] courseList;
    private int layoutResourceId;

    public AddExamSpinnerAdapter(Context context, int layoutResourceId, String[] list) {
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

        TextView course = (TextView)convertView.findViewById(R.id.courseSpinnerCourse);
        View color = convertView.findViewById(R.id.courseSpinnerColor);

        course.setText(this.courseList[position]);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_coursespinner, parent, false);
        }

        TextView course = (TextView)convertView.findViewById(R.id.courseSpinnerCourse);
        View color = convertView.findViewById(R.id.courseSpinnerColor);

        course.setText(this.courseList[position]);

        return convertView;
    }
}
