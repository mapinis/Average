package com.nodomain.mark.mka_class_tool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton addCourse = (FloatingActionButton) findViewById(R.id.addCourseFAB);
        courseList = new ArrayList<>();

        courseListView = (ListView) findViewById(R.id.courseListView);
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Code for class layout goes here
                layoutInflate courseLayout.xml
                edit TextViews and other stuff
                edit backButton in top left
                setContentView
                OR: USE FRAGMENTS (seems like the cool way to do it)*/
                Log.i("click", "click");
            }
        });

        courseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //THIS IS ALL DEBUG STUFF, DELETE LATER
                deleteFile("courseData.txt");
                onResume();
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

        CourseListAdapter cvAdapter = new CourseListAdapter(this, R.layout.course_cv, courseList);
        courseListView.setAdapter(cvAdapter);
        Log.i("adapterSet", "adapterSet");

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
