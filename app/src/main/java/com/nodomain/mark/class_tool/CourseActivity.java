package com.nodomain.mark.class_tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Mark on 9/18/2016.
 */
public class CourseActivity extends Activity{
    AssessmentListAdapter assessmentAdapter;
    course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        course = (course)getIntent().getSerializableExtra("course");
        assessmentAdapter = new AssessmentListAdapter(this, R.layout.assessment_item, course.assessments);
        ListView assessmentLV = (ListView) findViewById(R.id.assessmentsListView);
        assessmentLV.setAdapter(assessmentAdapter);

        TextView name = (TextView) findViewById(R.id.name);
        TextView teacher = (TextView) findViewById(R.id.teacher);
        TextView block = (TextView) findViewById(R.id.block);
        TextView room = (TextView) findViewById(R.id.room);
        final TextView average = (TextView) findViewById(R.id.average);
        FloatingActionButton addAssessment = (FloatingActionButton) findViewById(R.id.addAssessmentFAB);
        addAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseActivity.this);
                final LinearLayout assessmentDialog = (LinearLayout) LayoutInflater.from(CourseActivity.this).inflate(R.layout.assessment_dialog, null);
                builder.setView(assessmentDialog);
                builder.setTitle("Add Assessment");
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText nameET = (EditText) assessmentDialog.findViewById(R.id.nameET);
                        EditText worthET = (EditText) assessmentDialog.findViewById(R.id.worthET);
                        EditText gottenET = (EditText) assessmentDialog.findViewById(R.id.gottenET);

                        try {
                            int worth = Integer.parseInt(worthET.getText().toString());
                            int gotten = Integer.parseInt(gottenET.getText().toString());
                            course.addAssessment(nameET.getText().toString(), worth, gotten);
                        } catch (NumberFormatException e){
                            try {
                                int fail = Integer.parseInt(worthET.getText().toString());
                                gottenET.setError("Not a number");
                            } catch (NumberFormatException f){
                                worthET.setError("Not a number");
                            }
                        }
                        assessmentAdapter.notifyDataSetChanged();
                        average.setText((course.average < 0) ? "N/A" : Integer.toString((int)course.average) + "%");
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        name.setText(course.name);
        teacher.setText("Teacher: " + course.teacher);
        block.setText("Block " + course.block);
        room.setText("Room " + course.room);
        average.setText((course.average < 0) ? "N/A" : Integer.toString((int)course.average) + "%");
    }
}
