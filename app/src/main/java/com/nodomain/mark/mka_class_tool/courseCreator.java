package com.nodomain.mark.mka_class_tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by Mark on 9/9/2016.
 */
public class courseCreator extends Activity{
    final String fileName = "courseData.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.course_creator);

        final ArrayList<course> courseList = (ArrayList<course>)getIntent().getSerializableExtra("courseList");

        final EditText etName = (EditText) findViewById(R.id.cName);
        final EditText etRoom = (EditText) findViewById(R.id.cRoom);
        final EditText etTeacher = (EditText) findViewById(R.id.cTeacher);
        final EditText etBlock = (EditText) findViewById(R.id.cBlock);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout((int)(dm.widthPixels*.9), (int)(dm.heightPixels*0.6));

        Button add = (Button) findViewById(R.id.addCourse);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                deleteFile(fileName);
                Log.i("dir", getFilesDir().toString());
                courseList.add(new course(etName.getText().toString(),
                        Integer.parseInt(etBlock.getText().toString()),
                        etTeacher.getText().toString(), etRoom.getText().toString()));

                try{
                    OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE));

                    JSONObject courseListObj = new JSONObject();
                    JSONArray courseArray = new JSONArray();
                    for (course course : courseList){
                        JSONObject courseObj = new JSONObject();
                        courseObj.put("name", course.name);
                        courseObj.put("block", course.block);
                        courseObj.put("teacher", course.teacher);
                        courseObj.put("room", course.room);
                        courseArray.put(courseObj);
                    }
                    courseListObj.put("courses", courseArray);

                    osw.write(courseListObj.toString());
                    osw.close();

                    Log.i("JSONData", courseListObj.toString());
                    onBackPressed();
                } catch(Exception e){
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
