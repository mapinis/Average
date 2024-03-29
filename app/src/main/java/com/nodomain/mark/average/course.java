package com.nodomain.mark.average;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mark on 9/9/2016.
 */



public class course implements Serializable{

     protected class assessment implements Serializable{

        public int pointsWorth;
        public int pointsGotten;
        public String name;
        public float percentage;

        public assessment(String name, int pointsWorth, int pointsGotten){
            this.pointsWorth = pointsWorth;
            this.pointsGotten = pointsGotten;
            this.percentage = (float)pointsGotten/pointsWorth;
            this.name = name;
        }
    }

    public String name;
    public int block;
    public String teacher;
    public String room;
    public float average;
    public ArrayList<assessment> assessments;


    public course(String name, int block, String teacher, String room){
        this.name = name;
        this.block = block;
        this.teacher = teacher;
        this.room = room;
        assessments = new ArrayList<>();
        average = -1;
    }

    public void addAssessment(String name, int pointsWorth, int pointsGotten) {
        assessments.add(new assessment(name, pointsWorth, pointsGotten));
        int totalPointsGotten = 0;
        int totalPointsWorth = 0;
        for(assessment assessment : assessments) {
            totalPointsGotten += assessment.pointsGotten;
            totalPointsWorth += assessment.pointsWorth;
        }

        average = ((float)totalPointsGotten / totalPointsWorth) * 100;
    }

    public void deleteAssessment(assessment assessment) {
        assessments.remove(assessment);
        if(assessments.size() > 0) {
            int totalPointsGotten = 0;
            int totalPointsWorth = 0;
            for(assessment ass : assessments) { //hehe
                totalPointsGotten += ass.pointsGotten;
                totalPointsWorth += ass.pointsWorth;
            }
            average = (totalPointsGotten / totalPointsWorth) * 100;
        }
        else{
            average = -1;
        }
    }
}
