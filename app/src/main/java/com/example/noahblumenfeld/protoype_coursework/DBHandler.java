package com.example.noahblumenfeld.protoype_coursework;

import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * This class contains all the strings related to creating and manipulating the database for the
 * entire app.
 * Created by noahblumenfeld on 3/9/17.
 */

public class DBHandler extends SQLiteOpenHelper {

    //Database name and version
    private static final int DATABASE_VERSION = 14;
    private static final String DATABASE_NAME = "Buy_Used_Textbooks";

    //Table Names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_COLLEGES = "Colleges";
    private static final String TABLE_POSTS = "Posts";

    //Common Column Names
    private static final String KEY_ID = "_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_COLLEGE ="college";

    //Users Table Columns
    private static final String KEY_F_NAME = "fName";
    private static final String KEY_L_NAME = "lName";
    private static final String KEY_PASSWORD = "password";

    //Colleges Table Columns
    private static final String KEY_COLLEGE_EMAIL = "college_email";

    //Posts Table Columns
    private static final String KEY_BOOK_NAME = "book_name";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_EDITION = "edition";
    private static final String KEY_CONDITION = "condition";
    private static final String KEY_COURSE_ID = "course_id";
    private static final String KEY_PRICE = "price";
    private static final String KEY_NOTES = "notes";



    //Create Table Strings
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_F_NAME + " TEXT, " + KEY_L_NAME + " TEXT, " + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT, " + KEY_COLLEGE + " TEXT)";
    private static final String CREATE_TABLE_COLLEGES = "CREATE TABLE " + TABLE_COLLEGES + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_COLLEGE + " TEXT, " + KEY_COLLEGE_EMAIL + " TEXT)";
    private static final String CREATE_TABLE_POSTS = "CREATE TABLE " + TABLE_POSTS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_BOOK_NAME + " TEXT, " + KEY_AUTHOR + " TEXT, " + KEY_EDITION + " TEXT, "
            + KEY_CONDITION + " TEXT, " + KEY_COURSE_ID + " TEXT, " + KEY_PRICE + " REAL, " + KEY_NOTES + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_COLLEGE + " TEXT)";


    //constructor
    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_COLLEGES);
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_POSTS);
        //Create Colleges Table
        String[] colleges = {"Westminster College","UC Santa Barbara", "Edinburgh Napier University", "Cornell University"};
        String[] emails = {"@westminstercollege.edu","@umail.ucsb.edu","@live.napier.ac.uk","@cornell.edu"};
        for(int i = 0; i < colleges.length; i++){
            ContentValues values = new ContentValues();
            values.put(KEY_COLLEGE, colleges[i]);
            values.put(KEY_COLLEGE_EMAIL, emails[i]);
            sqLiteDatabase.insert(TABLE_COLLEGES,null,values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLEGES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        onCreate(sqLiteDatabase);
    }


    public void registerUser(User newUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_F_NAME, newUser.getfName());
        values.put(KEY_L_NAME, newUser.getlName());
        values.put(KEY_EMAIL, newUser.getEmail());
        values.put(KEY_PASSWORD, newUser.getPassword());
        values.put(KEY_COLLEGE, newUser.getCollege());
//        Log.d("Values check", values.toString());
        db.insert(TABLE_USERS,null,values);
        db.close();
    }


    /**
     * Checks for a users password
     */
    public Cursor checkUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT IFNULL( (SELECT " + KEY_PASSWORD + " FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = '" + email + "') ,'not found')";
        Cursor cursor = db.rawQuery(queryString, null);
        return cursor;
    }

    /**
     * Returns the college of a user
     */
    public String getUserCollege(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + KEY_COLLEGE + " FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        String college = cursor.getString(0);
        return college;
    }

    /**
     * Checks if a user exists
     */
    public Boolean checkExists(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT IFNULL( (SELECT " + KEY_ID + " FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = '" + email + "') ,'not found')";
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
//        Log.d("check", cursor.getString(0));
        if(cursor.getString(0).equals("not found"))
            return false;
        else
            return true;
    }

    /**
     * Checks if the email is the correct email for the chosen university
     */
    public String checkEmail(String college){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + KEY_COLLEGE_EMAIL + " FROM " + TABLE_COLLEGES + " WHERE " + KEY_COLLEGE + " = '" + college + "'";
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    /**
     * Adds a new post to the Post table
     */
    public void makePost(Post post, String email, String college){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_NAME, post.getBookName());
        values.put(KEY_AUTHOR,post.getAuthor());
        values.put(KEY_EDITION,post.getEdition());
        values.put(KEY_CONDITION,post.getCondition());
        values.put(KEY_COURSE_ID, post.getCourseID());
        values.put(KEY_PRICE,post.getPrice());
        values.put(KEY_NOTES,post.getNotes());
        values.put(KEY_EMAIL, email);
        values.put(KEY_COLLEGE, college);
        db.insert(TABLE_POSTS,null,values);
        db.close();

    }

    /**
     * Returns a cursor containing all the information matching the user's search
     */
    public Cursor getSearchData(String searchType, String userSearch, String college, String user){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + KEY_ID + " AS _id, " + KEY_BOOK_NAME + ", " + KEY_CONDITION + ", " + KEY_PRICE + " FROM " + TABLE_POSTS + " WHERE " + searchType + " LIKE '%" + userSearch +
                "%' AND " + KEY_COLLEGE + " = '" + college +"' AND " + KEY_EMAIL + " != '" + user + "'";
//        Log.d("check string",queryString);
        Cursor cursor = db.rawQuery(queryString,null);
        return cursor;
    }

    /**
     * Returns all the information related to a specific post
     */
    public String getPostData(int key){
        String postString = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + KEY_AUTHOR + ", " + KEY_EDITION + ", " + KEY_CONDITION + ", " + KEY_PRICE + ", " + KEY_NOTES + " FROM " + TABLE_POSTS +
                " WHERE " + KEY_ID + " = '" + key + "'";
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToNext();
        postString = "Author: " + cursor.getString(cursor.getColumnIndexOrThrow(KEY_AUTHOR));
        postString = postString + ", Edition: " + cursor.getString(cursor.getColumnIndexOrThrow(KEY_EDITION));
        postString = postString + ", Condition: " + cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONDITION));
        postString = postString + ", Price: $" + cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICE));
        postString = postString + ", Notes: " + cursor.getString(cursor.getColumnIndexOrThrow(KEY_NOTES));
        db.close();
        return postString;
    }

    /**
     * Return all the posts that a user has within the database at the moment the method is called
     */
    public Cursor getUserPosts(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + KEY_ID + " AS _id, " + KEY_BOOK_NAME + ", " + KEY_CONDITION + ", " + KEY_PRICE + " FROM " + TABLE_POSTS + " WHERE " + KEY_EMAIL + " = '" + user + "'";
        Cursor cursor = db.rawQuery(queryString,null);
        return cursor;
    }

    /**
     * Deletes a user's post
     */
    public boolean deletePost(int postID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_POSTS, KEY_ID + " = " + postID,null) > 0;

    }

    /**
     * Returns the email of the seller of the chosen post
     */
    public String getSellerEmail(int postID){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + KEY_EMAIL + " FROM " + TABLE_POSTS + " WHERE " + KEY_ID + " = " + postID;
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }


    //Cool check I made to retrieve post data, but currently of no use. Thought I wanted to do something with it when it was a much simpler solution.
    /* public boolean checkPostData(int type, String data){
        String KEY_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        switch(type){
            case 1: KEY_DATA = KEY_BOOK_NAME;
            case 2: KEY_DATA = KEY_AUTHOR;
            case 3: KEY_DATA = KEY_EDITION;
            case 4: KEY_DATA = KEY_NOTES;
            default: KEY_DATA = KEY_BOOK_NAME;
        }
        String queryString = "SELECT IFNULL( (SELECT " + KEY_EMAIL + " FROM " + TABLE_POSTS + " WHERE " + KEY_DATA + " = '" + data + "'), 'not found')";
        Cursor cursor = db.rawQuery(queryString,null);
        cursor.moveToFirst();
        if(cursor.getString(0).equals("not found"))
            return false;
        else
            return true;
    }

    public boolean checkPostData(int type, Double data) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT IFNULL( (SELECT " + KEY_EMAIL + " FROM " + TABLE_POSTS + " WHERE " + KEY_PRICE + " = '" + data + "'), 'not found')";
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        if (cursor.getString(0).equals("not found"))
            return false;
        else
            return true;
    } */
}
