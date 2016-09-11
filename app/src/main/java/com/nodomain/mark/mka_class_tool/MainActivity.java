package com.nodomain.mark.mka_class_tool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<course> courseList;
    FrameLayout dimmer;
    ArrayList<LinearLayout> courseListElements;
    int lastCourseIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addCourse = (Button) findViewById(R.id.addCourseButton);
        dimmer = (FrameLayout) findViewById(R.id.dimmer);
        dimmer.getForeground().setAlpha(0);
        courseList = new ArrayList<>();
        courseListElements = new ArrayList<>();
        lastCourseIndex = 0;

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
                if(lastCourseIndex == 0){
                    courseList.clear();
                }
                for(int i = 0; i < courseListArray.length(); i++){
                    JSONObject courseObj = courseListArray.getJSONObject(i);
                    courseList.add(new course(courseObj.getString("name"),
                            courseObj.getInt("block"), courseObj.getString("teacher"),
                            courseObj.getString("room")));
                    //lastCourseIndex++;
                }

                //Display List of all classes
                for(int i = lastCourseIndex; i < courseList.size(); i++){
                    LinearLayout courseViewLayout = (LinearLayout) findViewById(R.id.courseViewLayout);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    LinearLayout courseListElement =  (LinearLayout) inflater.inflate(R.layout.courselist_element, null, false);

                    //Warning: shit about to get janky
                     TextView displayView;
                    for(int j = 0; j < courseListElement.getChildCount(); j++){
                        switch (courseListElement.getChildAt(j).getId()){
                            case R.id.nameDisp:
                                displayView = (TextView)courseListElement.getChildAt(j);
                                displayView.setText(courseList.get(i).name);
                                break;
                            case R.id.roomDisp:
                                displayView = (TextView)courseListElement.getChildAt(j);
                                displayView.setText("Room" + courseList.get(i).room);
                                break;
                            case R.id.gradeDisp:
                                displayView = (TextView)courseListElement.getChildAt(j);
                                break; //WIP
                            default: break;
                        }
                    }
                    courseListElement.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Open new layout with this info here
                        }
                    });

                    courseListElement.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            //Open menu to remove this course
                            //Clear courseListElements and lastCourseIndex and remove that course
                            //run onResume
                            return false;
                        }
                    });

                    courseListElements.add(courseListElement);
                    courseViewLayout.addView(courseListElement);
                }
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
