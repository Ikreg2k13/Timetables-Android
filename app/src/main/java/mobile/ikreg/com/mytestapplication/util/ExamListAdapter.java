package mobile.ikreg.com.mytestapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import mobile.ikreg.com.mytestapplication.ExamListActivity;
import mobile.ikreg.com.mytestapplication.R;
import mobile.ikreg.com.mytestapplication.database.CourseDataSource;
import mobile.ikreg.com.mytestapplication.database.ExamMemory;

public class ExamListAdapter extends ArrayAdapter<ExamMemory> {

    Calendar c = Calendar.getInstance(TimeZone.getDefault());
    private CourseDataSource courseSource = new CourseDataSource(this.getContext());
    private Context context;
    private List<ExamMemory> examList;
    private int layoutResourceId;
    private HashMap<Integer, Boolean> selection = new HashMap<Integer, Boolean>();

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
            convertView = inflater.inflate(R.layout.adapter_examlist_two, parent, false);
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;

        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        if (params == null) convertView.setLayoutParams(new ViewGroup.LayoutParams(screenWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        else params.width = screenWidth + 150;

        TextView course = (TextView)convertView.findViewById(R.id.exam_list_course);
        TextView date = (TextView)convertView.findViewById(R.id.exam_list_date);
        TextView time = (TextView)convertView.findViewById(R.id.exam_list_time);
        TextView daysleft = (TextView)convertView.findViewById(R.id.exam_list_daysleft);
        TextView room = (TextView)convertView.findViewById(R.id.exam_list_room);
        View color = convertView.findViewById(R.id.colorView);

        course.setText(courseSource.getCourseById(this.examList.get(position).getCourseId()).getName());
        color.setBackgroundColor((int) courseSource.getCourseById(this.examList.get(position).getCourseId()).getColor());
        date.setText(ParseHelper.parseLongDateToString(this.examList.get(position).getDate()));
        time.setText(this.examList.get(position).getTime());
        if(this.examList.get(position).getExpired() == 0)
            daysleft.setText(DateHelper.getDaysLeft(c.getTimeInMillis(),this.examList.get(position).getDate()));
        else daysleft.setText(" - expired");
        room.setText("R " + ParseHelper.parseLongToString(3, this.examList.get(position).getRoom()));

        return convertView;
    }

    public void setNewSelection(int position, boolean value) {
        selection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = selection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition() {
        return selection.keySet();
    }

    public void removeSelection(int position) {
        selection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }

    public int getHMapSize() {
        return selection.size();
    }
}
