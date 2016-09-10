package com.nodomain.mark.mka_class_tool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<course> courseList;
    PopupWindow courseCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addCourse = (Button) findViewById(R.id.addCourseButton);
        courseList = new ArrayList<>();

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, courseCreator.class));

                /*Button addCourseConfirm = (Button) findViewById(R.id.addCourse);
                addCourseConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Create Course Here
                    }
                });

                Button cancel = (Button) findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });*/
            }
        });
    }

    public void addCourse(View v){

    }
}
