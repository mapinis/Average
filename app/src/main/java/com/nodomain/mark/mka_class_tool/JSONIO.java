package com.nodomain.mark.mka_class_tool;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Mark on 9/17/2016.
 */
public class JSONIO {

    FileInputStream input;
    InputStreamReader inputRead;
    BufferedReader buffRead;
    JSONObject JSONData;

    public JSONIO(FileInputStream input){
        try {
            this.input = input;
            this.inputRead = new InputStreamReader(this.input);
            this.buffRead = new BufferedReader(this.inputRead);
            this.JSONData = new JSONObject();
            readJSON();
        } catch (NullPointerException e){
            this.inputRead = null;
            this.buffRead = null;
            this.JSONData = new JSONObject();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void readJSON(){
        try {
            if (input != null) {
                StringBuilder sBuilder = new StringBuilder();
                String receivedString = "";
                sBuilder.append(receivedString);
                receivedString = buffRead.readLine();
                while (receivedString != null) {
                    sBuilder.append(receivedString);
                    receivedString = buffRead.readLine();
                }
                String deletthis = sBuilder.toString();
                Log.i("JSONData", deletthis);
                JSONData = new JSONObject(deletthis);
                Log.i("JSONData", deletthis);

                input.getChannel().position(0);
                buffRead = new BufferedReader(new InputStreamReader(input));
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void writeJSON(OutputStreamWriter osw){
        try{
            osw.write(JSONData.toString());
            osw.close();
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ArrayList<course> getCourseList(){
        readJSON();
        ArrayList<course> courseList = new ArrayList<>();
        if(input != null) {
            try {
                JSONArray courseListArray = JSONData.getJSONArray("courses");
                for (int i = 0; i < courseListArray.length(); i++) {
                    JSONObject courseObj = courseListArray.getJSONObject(i);
                    Log.i("addingCourse", courseObj.toString());
                    courseList.add(new course(courseObj.getString("name"),
                            courseObj.getInt("block"), courseObj.getString("teacher"),
                            courseObj.getString("room")));
                }
            } catch(Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return courseList;
    }

    public void addCourse(String name, int block, String teacher, String room){
        readJSON();
        try {
            JSONArray courseListArray;
            if (JSONData.has("courses")){
                courseListArray = JSONData.getJSONArray("courses");
            } else {
                courseListArray = new JSONArray();
            }
            JSONObject courseObj = new JSONObject();
            courseObj.put("name", name);
            courseObj.put("block", block);
            courseObj.put("teacher", teacher);
            courseObj.put("room", room);
            courseListArray.put(courseObj);
            JSONData.remove("courses");
            JSONData.put("courses", courseListArray);
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void deleteCourse(course course){
        try{
            JSONArray courseListArray = JSONData.getJSONArray("courses");
            for(int i = 0; i < courseListArray.length(); i++){
                JSONObject courseObj = courseListArray.getJSONObject(i);
                if(courseObj.get("name") == course.name
                        && courseObj.get("room") == course.room
                        && courseObj.get("teacher") == course.teacher
                        && courseObj.get("block").toString() == Integer.toString(course.block)){
                    courseListArray.remove(i);
                    JSONData.remove("courses");
                    JSONData.put("courses", courseListArray);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void addAssessment(course course){
        //NOT DOING THIS YET
    }

    public void deleteAssessment(course course, String assessmentName){
        readJSON();
        //NOT DOING THIS YET
    }

    public void close(){
        try{
            input.close();
            inputRead.close();
            buffRead.close();
        } catch(Exception e){}
    }
}
