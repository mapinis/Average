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

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
                StringWriter writer = new StringWriter();
                try {
                    deleteFile(fileName);
                    courseList.add(new course(etName.getText().toString(),
                            Integer.parseInt(etBlock.getText().toString()),
                            etTeacher.getText().toString(), etRoom.getText().toString()));

                    Log.i("dir", getFilesDir().toString());
                    FileOutputStream fos = openFileOutput(fileName, MODE_APPEND);
                    XmlSerializer serializer = Xml.newSerializer();
                    serializer.setOutput(writer);
                    serializer.startDocument(null, Boolean.valueOf(true));
                    serializer.startTag(null, "courseList");

                    for(course course: courseList){
                        serializer.startTag(null, "course");
                        serializer.attribute(null, "name", course.getName());
                        serializer.attribute(null, "room", course.getRoom());
                        serializer.attribute(null, "teacher", course.getTeacher());
                        serializer.attribute(null, "block", Integer.toString(course.getBlock()));
                        serializer.endTag(null, "course");
                    }
                    serializer.endTag(null, "courseList");
                    serializer.endDocument();
                    serializer.flush();
                    fos.write(writer.toString().getBytes());
                    fos.close();
                    Log.i("dir", getFilesDir().toString());
                    onBackPressed();
                } catch (Exception e){
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
