package com.example.noahblumenfeld.protoype_coursework;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Screen that searches the database by book name and displays results to the user
 * Created by Noah Blumenfeld
 */
public class BookNameSearchActivity extends AppCompatActivity {

    //instance variables
    private EditText searchField;
    private ListView postListView;
    private Context context;
    private String currentUser, userCollege, searchText;
    private DBHandler db;
    private int postID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_name_search);
        setTitle("Book Search");
        context = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUser = extras.getString("EXTRA_EMAIL");
            userCollege = extras.getString("EXTRA_USER_COLLEGE");
        }

        postListView = (ListView) findViewById(R.id.post_listview);

        searchField = (EditText) findViewById(R.id.search);

        //initiate user search
        Button searchButton = (Button) findViewById(R.id.start_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchField.getText().toString();
                postSearchClick();
            }
        });

        //Create dialog box that displays all the information from that post
        postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db = new DBHandler(context);
                TextView mbookName = (TextView) view.findViewById(R.id.post_book);
                TextView mId = (TextView) view.findViewById(R.id.individual_post_id);
                String bookName = mbookName.getText().toString();
                postID = Integer.parseInt(mId.getText().toString());

                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle(bookName);
                String stringPostId = db.getPostData(postID);
                dialog.setMessage(stringPostId);
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Contact seller", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent contactSellerActivity = new Intent(BookNameSearchActivity.this, ContactSellerActivity.class);
                        contactSellerActivity.putExtra("SELLER_EMAIL", db.getSellerEmail(postID));
                        startActivity(contactSellerActivity);
                    }
                });
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        return;
                    }
                });
                dialog.show();
            }

            });
        }

    /**
     * Search the database for the user's inputted text
     */
    private void postSearchClick() {
        //Create cursor
        DBHandler db = new DBHandler(context);
        Cursor cursor = db.getSearchData("book_name", searchText, userCollege, currentUser);
        //Create cursor adapter
        PostCursorAdapter adapter = new PostCursorAdapter(context,cursor);
        //populate screen with results (if there are any)
        postListView.setAdapter(adapter);

    }
}
