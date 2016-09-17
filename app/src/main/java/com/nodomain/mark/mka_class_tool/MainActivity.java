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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<course> courseList;
    InputStream input;
    InputStreamReader inputRead;
    BufferedReader buffRead;
    ListView courseListView;
    CourseListAdapter cvAdapter;

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
                                .setMessage("Are you sure you want to delete course '" + courseList.get(position).name + "' permanently? \n \nYou will lose all assignments and data.")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        courseList.remove(position);
                                        cvAdapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();

                        //DELETE course here
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

        courseList.clear();

        try {
            input = openFileInput("courseData.txt");
            inputRead = new InputStreamReader(input);
            buffRead = new BufferedReader(inputRead);
        } catch (FileNotFoundException e){
            Log.i("courseData.txt", "not found");
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String JSONData = "";
        try {
            if (input != null) {
                String receivedString = "";
                StringBuilder sBuilder = new StringBuilder();

                while ((receivedString = buffRead.readLine()) != null) {
                    sBuilder.append(receivedString);
                }
                input.close();
                buffRead.close();
                inputRead.close();
                JSONData = sBuilder.toString();

                JSONObject courseListData = new JSONObject(JSONData);
                JSONArray courseListArray = courseListData.getJSONArray("courses");
                for (int i = 0; i < courseListArray.length(); i++) {
                    JSONObject courseObj = courseListArray.getJSONObject(i);
                    Log.i("addingCourse", courseObj.toString());
                    courseList.add(new course(courseObj.getString("name"),
                            courseObj.getInt("block"), courseObj.getString("teacher"),
                            courseObj.getString("room")));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        courseListView.setAdapter(cvAdapter);
        Log.i("adapterSet", "adapterSet");

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
