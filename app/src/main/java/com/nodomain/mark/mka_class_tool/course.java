package com.nodomain.mark.mka_class_tool;

import java.util.ArrayList;

/**
 * Created by Mark on 9/9/2016.
 */



public class course {

    private class assignment {

        public int pointsWorth;
        public int pointsGotten;
        public String name;
        public float percentage;

        public assignment(String name, int pointsWorth, int pointsGotten){
            this.pointsWorth = pointsWorth;
            this.pointsGotten = pointsGotten;
            this.percentage = pointsGotten/pointsWorth;
            this.name = name;
        }
    }

    public String name;
    public int block;
    public String teacher;
    public String room;
    public float average;
    public ArrayList<assignment> assignments;


    public course(String name, int block, String teacher, String room){
        this.name = name;
        this.block = block;
        this.teacher = teacher;
        this.room = room;
        assignments = new ArrayList<>();
        average = -1;
    }

    public void addAssignment(String name, int pointsWorth, int pointsGotten) {
        assignments.add(new assignment(name, pointsWorth, pointsGotten));
        int totalPointsGotten = 0;
        int totalPointsWorth = 0;
        for(assignment assignment : assignments) {
            totalPointsGotten += assignment.pointsGotten;
            totalPointsWorth += assignment.pointsWorth;
        }

        average = totalPointsGotten / totalPointsWorth;
    }
}
