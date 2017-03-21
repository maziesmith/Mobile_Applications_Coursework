package com.example.noahblumenfeld.protoype_coursework;

/**
 * Class used to input data into the User table in DBHandler
 * Created by noahblumenfeld on 3/9/17.
 */

public class User {

    //instance variables
    private Integer userID;
    private String fName, lName, email, password, college;

    //constructors
    public User(){
    }

    public User(Integer userID, String fName, String lName, String email, String password, String college) {
        this.userID = userID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.college = college;
    }

    //setter methods (could be used to change various fields if the user wished to)
    public void setfName(String fName){
        this.fName = fName;
    }

    public void setlName(String lName){
        this.lName = lName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }


    //getter methods
    public String getfName(){
        return fName;
    }

    public String getlName(){
        return lName;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getCollege(){
        return college;
    }


}
