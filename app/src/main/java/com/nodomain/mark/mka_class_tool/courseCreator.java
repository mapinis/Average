package com.nodomain.mark.mka_class_tool;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Mark on 9/9/2016.
 */
public class courseCreator extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.course_creator);

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
                String JSONData = "";
                try {
                    InputStream input = openFileInput("courseData.txt");
                    InputStreamReader inputRead  = new InputStreamReader(input);
                    BufferedReader buffRead = new BufferedReader(inputRead);

                    if(input != null){
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
                        JSONObject courseObj = new JSONObject();
                        courseObj.put("name", etName.getText());
                        courseObj.put("block", etBlock.getText());
                        courseObj.put("teacher", etTeacher.getText());
                        courseObj.put("room", etRoom.getText());
                        courseListArray.put(courseObj);
                        courseListData.remove("courses");
                        courseListData.put("courses", courseListArray);

                        OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("courseData.txt", MODE_PRIVATE));
                        osw.write(courseListData.toString());
                        osw.close();

                        Log.i("JSONData", courseListData.toString());
                    }

                } catch(FileNotFoundException e) {
                } catch (Exception e){
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
