package com.example.noahblumenfeld.protoype_coursework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Intermediate screen that allows users to choose the type of search they want
 * Created by Noah Blumenfeld
 */
public class SearchActivity extends AppCompatActivity {

    //instance variables
    private String currentUser, userCollege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        setTitle("Search by:");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUser = extras.getString("EXTRA_EMAIL");
            userCollege = extras.getString("EXTRA_USER_COLLEGE");
        }
        //navigate to the search by book name screen
        final Button bookName = (Button) findViewById(R.id.book_name_button);
        bookName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookNameSearch = new Intent(SearchActivity.this, BookNameSearchActivity.class);
                bookNameSearch.putExtra("EXTRA_EMAIL",currentUser);
                bookNameSearch.putExtra("EXTRA_USER_COLLEGE", userCollege);
                startActivity(bookNameSearch);
            }
        });

        //navigate to the search by course ID screen
        final Button courseID = (Button) findViewById(R.id.course_id_button);
        courseID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent courseIDSearch = new Intent(SearchActivity.this, CourseIDSearchActivity.class);
                courseIDSearch.putExtra("EXTRA_EMAIL",currentUser);
                courseIDSearch.putExtra("EXTRA_USER_COLLEGE", userCollege);
                startActivity(courseIDSearch);
            }
        });
    }
}
