package com.nodomain.mark.average;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;

/**
 * Created by Mark on 9/9/2016.
 */
public class courseCreator extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.course_creator);

        final EditText etName = (EditText) findViewById(R.id.cName);
        final EditText etRoom = (EditText) findViewById(R.id.cRoom);
        final EditText etTeacher = (EditText) findViewById(R.id.cTeacher);
        final EditText etBlock = (EditText) findViewById(R.id.cBlock);

        Button add = (Button) findViewById(R.id.addCourse);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //MAKE SURE TO TEST THAT BLOCK is INT, and NAME not TAKEN
                course newCourse = new course(etName.getText().toString(), Integer.parseInt(etBlock.getText().toString()),
                        etTeacher.getText().toString(), etRoom.getText().toString());
                try {

                    JSONIO JSONIO = new JSONIO(openFileInput("courseData.txt"));
                    JSONIO.addCourse(newCourse);
                    deleteFile("courseData.txt");
                    JSONIO.writeJSON(new OutputStreamWriter(openFileOutput("courseData.txt", MODE_APPEND)));

                } catch(FileNotFoundException e){
                    Log.i("courseData.txt", "not found");
                    try{
                        JSONIO JSONIO = new JSONIO(null);
                        JSONIO.addCourse(newCourse);
                        deleteFile("courseData.txt");
                        JSONIO.writeJSON(new OutputStreamWriter(openFileOutput("courseData.txt", MODE_APPEND)));
                    } catch(FileNotFoundException f){}
                } catch(Exception e){
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

                onBackPressed();
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
