package com.nodomain.mark.mka_class_tool;

import java.io.Serializable;

/**
 * Created by Mark on 9/9/2016.
 */
public class course implements Serializable{
    public String name;
    public int block;
    public String teacher;
    public String room;


    public course(String name, int block, String teacher, String room){
        this.name = name;
        this.block = block;
        this.teacher = teacher;
        this.room = room;
    }
}
