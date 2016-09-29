package com.nodomain.mark.mka_class_tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Mark on 9/18/2016.
 */
public class CourseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        course course = (course)getIntent().getSerializableExtra("course");

        TextView name = (TextView) findViewById(R.id.name);
        TextView teacher = (TextView) findViewById(R.id.teacher);
        TextView block = (TextView) findViewById(R.id.block);
        TextView room = (TextView) findViewById(R.id.room);
        TextView average = (TextView) findViewById(R.id.average);

        name.setText(course.name);
        teacher.setText("Teacher: " + course.teacher);
        block.setText("Block " + course.block);
        room.setText("Room " + course.room);
        average.setText((course.average < 0) ? "N/A" : Integer.toString((int)course.average) + "%");
    }
}
