package com.nodomain.mark.mka_class_tool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<course> courseList;
    ListView courseListView;
    CourseListAdapter cvAdapter;
    JSONIO JSONIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton addCourse = (FloatingActionButton) findViewById(R.id.addCourseFAB);
        courseList = new ArrayList<>();
        cvAdapter = new CourseListAdapter(this, R.layout.course_cv, courseList);

        courseListView = (ListView) findViewById(R.id.courseListView);
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Code for class layout goes here
                layoutInflate courseLayout.xml
                edit TextViews and other stuff
                edit backButton in top left
                setContentView
                OR: USE FRAGMENTS (seems like the cool way to do it)
                OR: Just make an activity and pass it the information*/
                Intent intent = new Intent(MainActivity.this, CourseActivity.class);
                intent.putExtra("course", courseList.get(position));
                startActivity(intent);
                MainActivity.this.overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                Log.i("click", "click");
            }
        });

        courseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final LinearLayout parentLayout = (LinearLayout) view.findViewById(R.id.parentLayout);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout deleteCourse = (LinearLayout) inflater.inflate(R.layout.delete_course, null, false);

                deleteCourse.findViewById(R.id.cancelDeletion).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parentLayout.removeView(deleteCourse);
                    }
                });
                deleteCourse.findViewById(R.id.deleteCourse).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parentLayout.removeView(deleteCourse);
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(R.mipmap.ic_delete_black_24dp)
                                .setTitle("Delete Course")
                                .setMessage("Are you sure you want to delete course '" + courseList.get(position).name + "' permanently? \n \nAll assessments and data will be lost.")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        JSONIO.deleteCourse(courseList.get(position));
                                        deleteFile("courseData.txt");
                                        try {
                                            JSONIO.writeJSON(new OutputStreamWriter(openFileOutput("courseData.txt", MODE_APPEND)));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            throw new RuntimeException(e);
                                        }
                                        courseList.remove(position);
                                        cvAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();

                    }
                });
                parentLayout.addView(deleteCourse);
                return false;
            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, courseCreator.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        try {
            JSONIO = new JSONIO(openFileInput("courseData.txt"));
        } catch (FileNotFoundException e) {
            Log.i("courseData.txt", "not found");
            JSONIO = new JSONIO(null);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        courseList = JSONIO.getCourseList();

        cvAdapter = new CourseListAdapter(this, R.layout.course_cv, courseList);
        courseListView.setAdapter(cvAdapter);
        cvAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
