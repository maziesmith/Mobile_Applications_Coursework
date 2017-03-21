package com.example.noahblumenfeld.protoype_coursework;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

/**
 * Screen that allows users to add a new post
 * Created by Noah Blumenfeld
 */
public class PostActivity extends AppCompatActivity {

    //instance variables
    private Spinner mCondition;
    private EditText mBookName, mAuthor, mEdition, mCourseID, mPrice, mNotes;
    private String bookName, author, edition, condition, courseID, priceString, notes;
    private String currentUser, userCollege;
    private Double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setTitle("Create a Post");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            currentUser = extras.getString("EXTRA_EMAIL");
            userCollege = extras.getString("EXTRA_USER_COLLEGE");

        }

        mBookName = (EditText) findViewById(R.id.et1);
        mAuthor = (EditText) findViewById(R.id.et2);
        mEdition = (EditText) findViewById(R.id.et3);
        mCondition = (Spinner) findViewById(R.id.spin1);
        mCourseID = (EditText) findViewById(R.id.et5);
        mPrice = (EditText) findViewById(R.id.et6);
        mNotes = (EditText) findViewById(R.id.et7);

        Button postButton = (Button) findViewById(R.id.post_button);
        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                bookName = mBookName.getText().toString();
                author = mAuthor.getText().toString();
                edition = mEdition.getText().toString();
                condition = mCondition.getSelectedItem().toString();
                courseID = mCourseID.getText().toString();
                priceString = mPrice.getText().toString();
                //check the post for any errors or missing text fields
                if(checkBookName(bookName) && checkAuthor(author) && checkEdition(edition) && checkCourseID(courseID) && checkPrice(priceString)) {
                    price = Double.parseDouble(priceString);
                    notes = mNotes.getText().toString();
                    //attempt to add to database
                    if (attemptPost()) {
                        Intent menuActivity = new Intent(PostActivity.this, MenuActivity.class);
                        menuActivity.putExtra("EXTRA_EMAIL", currentUser);
                        menuActivity.putExtra("EXTRA_USER_COLLEGE", userCollege);
                        Toast.makeText(PostActivity.this, "Post Successful!", Toast.LENGTH_LONG).show();
                        startActivity(menuActivity);
                    }
                }

            }
        });


    }

    /**
     * This method attempts to add the post to the database
     */
    private boolean attemptPost(){
            UserPostTask post = new UserPostTask(bookName, author, edition, condition, courseID, price, notes);
            if (post.doInBackground())
                return true;
            else
                return false;
    }

    /**
     * This method checks the book name to make sure it isn't an empty text field
     */
    private boolean checkBookName(String bookName){
        if(bookName.equals("")){
            mBookName.setError(getString(R.string.post_error));
            return false;
        }
        else
            return true;
    }

    /**
     * This method checks the author to make sure it isn't an empty text field
     */
    private boolean checkAuthor(String author){
        if(author.equals("")){
            mAuthor.setError(getString(R.string.post_error));
            return false;
        }
        else
            return true;
    }

    /**
     * This method checks the edition to make sure it isn't an empty text field
     */
    private boolean checkEdition(String edition){
        if(edition.equals("")){
            mEdition.setError(getString(R.string.post_error));
            return false;
        }
        else
            return true;
    }

    /**
     * This method checks the courseID to make sure it isn't an empty text field
     */
    private boolean checkCourseID(String courseID){
        if(courseID.equals("")){
            mCourseID.setError(getString(R.string.post_error));
            return false;
        }
        else
            return true;
    }

    /**
     * This method checks the Price to make sure it isn't an empty text field
     */
    private boolean checkPrice(String price){
        if(price.equals("")){
            mPrice.setError(getString(R.string.post_error));
            return false;
        }
        else
            return true;
    }


    /**
     * Inner class that helps add the post to the database
     */
    public class UserPostTask extends AsyncTask<Void, Void, Boolean> {

        //instance variables
        private final String bookName;
        private final String author;
        private final String edition;
        private final String condition;
        private final String courseID;
        private final Double price;
        private final String notes;

        //constructor
        UserPostTask(String bookName, String author, String edition, String condition, String courseID, Double price, String notes){
            this.bookName = bookName;
            this.author = author;
            this.edition = edition;
            this.condition = condition;
            this.courseID = courseID;
            this.price = price;
            this.notes = notes;
        }

        /**
         * This method adds the post to the database
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            Post post = new Post(bookName,author,edition,condition,courseID,price,notes);
            DBHandler db = new DBHandler(getBaseContext());
            db.makePost(post,currentUser, userCollege);
            return true;
        }
    }

}
