package com.nodomain.mark.average;

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
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;

/**
 * Created by Mark on 9/18/2016.
 */
public class CourseActivity extends Activity{
    AssessmentListAdapter assessmentAdapter;
    course thisCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.OnClickListener deleteAssessmentListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("deleteAssessment", "deleting");
            }
        };
        setContentView(R.layout.activity_course);
        thisCourse = (course)getIntent().getSerializableExtra("course");
        assessmentAdapter = new AssessmentListAdapter(this, R.layout.assessment_item, thisCourse.assessments,  deleteAssessmentListener);
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
                            boolean fail = false;
                            for(course.assessment assessment: thisCourse.assessments){
                                if(nameET.toString().equals(assessment.name)){
                                    nameET.setError("Name in use");
                                    fail = true;
                                    break;
                                }
                            }
                            if(!fail){
                                thisCourse.addAssessment(nameET.getText().toString(), worth, gotten);
                                try {

                                    JSONIO JSONIO = new JSONIO(openFileInput(  "courseData.txt"));
                                    JSONIO.addAssessment(thisCourse, thisCourse.assessments.size() - 1);
                                    deleteFile("courseData.txt");
                                    JSONIO.writeJSON(new OutputStreamWriter(openFileOutput("courseData.txt", MODE_APPEND)));

                                } catch(FileNotFoundException e){
                                    Log.i("courseData.txt", "not found");
                                    try{
                                        JSONIO JSONIO = new JSONIO(null);
                                        JSONIO.addAssessment(thisCourse, thisCourse.assessments.size() - 1);
                                        deleteFile("courseData.txt");
                                        JSONIO.writeJSON(new OutputStreamWriter(openFileOutput("courseData.txt", MODE_APPEND)));
                                    } catch(FileNotFoundException f){}
                                } catch(Exception e){
                                    e.printStackTrace();
                                    throw new RuntimeException(e);
                                }
                            }
                        } catch (NumberFormatException e){
                            try {
                                int fail = Integer.parseInt(worthET.getText().toString());
                                gottenET.setError("Not a number");
                            } catch (NumberFormatException f){
                                worthET.setError("Not a number");
                            }
                        }
                        assessmentAdapter.notifyDataSetChanged();
                        average.setText((thisCourse.average < 0) ? "N/A" : Integer.toString((int)+thisCourse.average) + "%");
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        name.setText(thisCourse.name);
        teacher.setText("Teacher: " + thisCourse.teacher);
        block.setText("Block " + thisCourse.block);
        room.setText("Room " + thisCourse.room);
        average.setText((thisCourse.average < 0) ? "N/A" : Integer.toString((int)thisCourse.average) + "%");
    }
}
