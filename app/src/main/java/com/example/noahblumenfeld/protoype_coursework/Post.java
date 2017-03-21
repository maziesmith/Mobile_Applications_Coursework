package com.example.noahblumenfeld.protoype_coursework;

/**
 * Class used to input data into the Post table in DBHandler
 * Created by noahblumenfeld on 3/12/17.
 */

public class Post {

    //instance variables
    public static final String KEY_BOOK_NAME = "book_name";
    public static final String KEY_EDITION = "edition";
    public static final String KEY_PRICE = "price";
    private String bookName, author, edition, condition, courseID, notes;
    private Double price;

    //constructor
    public Post(String bookName, String author, String edition, String condition, String courseID, Double price, String notes){
        this.bookName = bookName;
        this.author = author;
        this.edition = edition;
        this.condition = condition;
        this.courseID = courseID;
        this.price = price;
        this.notes = notes;
    }

    //getter methods
    public String getBookName(){return bookName;}

    public String getAuthor(){return author;}

    public String getEdition(){return edition;}

    public String getCondition(){return condition;}

    public String getCourseID(){return courseID;}

    public Double getPrice(){return price;}

    public String getNotes(){return notes;}
}
