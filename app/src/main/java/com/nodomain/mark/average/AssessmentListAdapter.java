package com.nodomain.mark.average;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mark on 10/1/2016.
 */
public class AssessmentListAdapter extends ArrayAdapter<course.assessment>{

    View.OnClickListener deleteAssessmentListener;
    public AssessmentListAdapter(Context context, int textViewResourceID) {
        super(context, textViewResourceID);
    }
    public AssessmentListAdapter(Context context, int resource, List<course.assessment> assessmentList, View.OnClickListener deleteAssessmentListener) {
        super(context, resource, assessmentList);
        this.deleteAssessmentListener = deleteAssessmentListener;
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
            ImageButton deleteAssessment = (ImageButton) assessmentListView.findViewById(R.id.deleteAssessment);
            deleteAssessment.setOnClickListener(deleteAssessmentListener);
            name.setText(currentAssessment.name);
            points.setText(currentAssessment.pointsGotten + "/" + currentAssessment.pointsWorth);
        }

        return assessmentListView;
    }
}
