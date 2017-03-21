package com.example.noahblumenfeld.protoype_coursework;

/**
 * Class used to input data into the College table in DBHandler
 * Created by noahblumenfeld on 3/10/17.
 */

public class College {

    //instance variables
    private Integer collegeID;
    private String name, email;

    //constructors
    public College(){}

    public College(Integer collegeID, String name, String email){
        this.collegeID = collegeID;
        this.name = name;
        this.email = email;
    }

    //getter methods
    public String getName(){return name;}
    public String getEmail(){
        return email;
    }
}
