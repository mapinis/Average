package com.nodomain.mark.mka_class_tool;

/**
 * Created by Mark on 9/9/2016.
 */
public class course {
    String name;
    int block;
    String teacher;
    int room;

    public course(String name, int block, String teacher, int room){
        this.name = name;
        this.block = block;
        this.teacher = teacher;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}