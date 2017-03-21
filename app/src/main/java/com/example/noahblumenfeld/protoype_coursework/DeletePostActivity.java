package com.example.noahblumenfeld.protoype_coursework;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Screen that allows users to delete one of their posts if they have one
 */
public class DeletePostActivity extends AppCompatActivity {

    //instance variables
    private ListView deletePostListView;
    private Context context;
    private String currentUser, userCollege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_post);
        context = this;
        setTitle("Delete a post");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUser = extras.getString("EXTRA_EMAIL");
            userCollege = extras.getString("EXTRA_USER_COLLEGE");
        }

        deletePostListView = (ListView) findViewById(R.id.delete_post_listview);
        populateUserDeleteView();

        //post selected, prompt user with option to delete the post or cancel
        deletePostListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final DBHandler db = new DBHandler(context);
                TextView mbookName = (TextView) view.findViewById(R.id.post_book);
                TextView mId = (TextView) view.findViewById(R.id.individual_post_id);
                String bookName = mbookName.getText().toString();
                final int intPostID = Integer.parseInt(mId.getText().toString());

                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle(bookName);
                String postId = db.getPostData(intPostID);
                dialog.setMessage(postId);
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deletePost(intPostID);
                        finish();
                        startActivity(getIntent());
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
     * Method to populate users screen with their posts
     */
    private void populateUserDeleteView(){
        //access database
        DBHandler db = new DBHandler(context);
        Cursor cursor = db.getUserPosts(currentUser);
        //create cursor adapter
        PostCursorAdapter adapter = new PostCursorAdapter(context,cursor);
        //populate list view with data from cursor (if there is any)
        deletePostListView.setAdapter(adapter);
    }
}
