package com.nodomain.mark.class_tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mark on 9/16/2016.
 */
public class CourseListAdapter extends ArrayAdapter<course>{

    public CourseListAdapter(Context context, int textViewResourceID) {
        super(context, textViewResourceID);
    }

    public CourseListAdapter(Context context, int resource, List<course> courseList) {
        super(context, resource, courseList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View courseCV = convertView;

        if(courseCV == null){
            courseCV = LayoutInflater.from(getContext()).inflate(R.layout.course_cv, null);
        }

        course currentCourse = getItem(position);

        if(currentCourse != null){
            TextView nameDisp = (TextView) courseCV.findViewById(R.id.nameDisp);
            TextView roomDisp = (TextView) courseCV.findViewById(R.id.roomDisp);
            TextView gradeDisp = (TextView) courseCV.findViewById(R.id.gradeDisp);

            nameDisp.setText(currentCourse.name);
            roomDisp.setText("Room " + currentCourse.room);
            String averageText;
            if(currentCourse.average < 0) {
                averageText = "";
            }
            else {
                averageText = (int)currentCourse.average + "%";
            }
            gradeDisp.setText(averageText);
        }

        return courseCV;
    }
}
