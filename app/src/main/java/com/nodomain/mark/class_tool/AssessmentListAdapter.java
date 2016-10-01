package com.nodomain.mark.class_tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mark on 10/1/2016.
 */
public class AssessmentListAdapter extends ArrayAdapter<course.assessment>{
    public AssessmentListAdapter(Context context, int textViewResourceID) {
        super(context, textViewResourceID);
    }
    public AssessmentListAdapter(Context context, int resource, List<course.assessment> assessmentList) {
        super(context, resource, assessmentList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View assessmentListView = convertView;

        if(assessmentListView == null){
            assessmentListView = LayoutInflater.from(getContext()).inflate(R.layout.assessment_item, null);
        }

        course.assessment currentAssessment = getItem(position);

        if(currentAssessment != null){
            TextView name = (TextView) assessmentListView.findViewById(R.id.assessmentName);
            TextView points = (TextView) assessmentListView.findViewById(R.id.assessmentPoints);

            name.setText(currentAssessment.name);
            points.setText(currentAssessment.pointsGotten + "/" + currentAssessment.pointsWorth);
        }

        return assessmentListView;
    }
}
