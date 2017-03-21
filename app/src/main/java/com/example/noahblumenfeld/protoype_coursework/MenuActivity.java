package com.example.noahblumenfeld.protoype_coursework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Screen for users to navigate between searching for, posting or deleting a book
 */
public class MenuActivity extends AppCompatActivity {

    //instance variables
    private String currentUser, userCollege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Menu");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            currentUser = extras.getString("EXTRA_EMAIL");
            userCollege = extras.getString("EXTRA_USER_COLLEGE");
        }

        //navigate to search screen
        Button bt1 = (Button) findViewById(R.id.search_button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchActivity = new Intent(MenuActivity.this , SearchActivity.class);
                searchActivity.putExtra("EXTRA_EMAIL",currentUser);
                searchActivity.putExtra("EXTRA_USER_COLLEGE", userCollege);
                startActivity(searchActivity);
            }

        });

        //navigate to post screen
        Button bt2 = (Button) findViewById(R.id.post_button);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postActivity = new Intent(MenuActivity.this , PostActivity.class);
                postActivity.putExtra("EXTRA_EMAIL",currentUser);
                postActivity.putExtra("EXTRA_USER_COLLEGE", userCollege);
                startActivity(postActivity);
            }

        });

        //navigate to delete screen
        Button bt3 = (Button) findViewById(R.id.delete_post_button);
        bt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent deletePostActivity = new Intent(MenuActivity.this , DeletePostActivity.class);
                deletePostActivity.putExtra("EXTRA_EMAIL",currentUser);
                deletePostActivity.putExtra("EXTRA_USER_COLLEGE", userCollege);
                startActivity(deletePostActivity);
            }
        });

    }
}
