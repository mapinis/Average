package com.nodomain.mark.mka_class_tool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

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
        dimmer.getForeground().setAlpha(0);
        super.onResume();
    }

    @Override
    protected void onPause() {
        dimmer.getForeground().setAlpha(220);
        super.onPause();
    }
}
