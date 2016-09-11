package com.nodomain.mark.mka_class_tool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<course> courseList;
    FrameLayout dimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addCourse = (Button) findViewById(R.id.addCourseButton);
        dimmer = (FrameLayout) findViewById(R.id.dimmer);
        dimmer.getForeground().setAlpha(0);
        courseList = new ArrayList<>();

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, courseCreator.class);
                intent.putExtra("courseList", courseList);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        courseList.clear();
        String JSONData = "";
        try {
            InputStream input = openFileInput("courseData.txt");
            if(input != null){
                InputStreamReader inputRead = new InputStreamReader(input);
                BufferedReader buffRead = new BufferedReader(inputRead);
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
                for(int i = 0; i < courseListArray.length(); i++){
                    JSONObject courseObj = courseListArray.getJSONObject(i);
                    courseList.add(new course(courseObj.getString("name"),
                            courseObj.getInt("block"), courseObj.getString("teacher"),
                            courseObj.getString("room")));
                }

                TextView tempCourseTV = (TextView) findViewById(R.id.tempCourseTV);
                String fin = "";
                for(course course : courseList){
                    fin += course.name;
                }
                tempCourseTV.setText(fin);
            }

        } catch(FileNotFoundException e) {
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        dimmer.getForeground().setAlpha(0);
        super.onResume();
    }

    @Override
    protected void onPause() {
        dimmer.getForeground().setAlpha(220);
        super.onPause();
    }
}
