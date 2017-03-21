package com.example.noahblumenfeld.protoype_coursework;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Class for handling populating the list view with data from the cursor
 * Created by noahblumenfeld on 3/16/17.
 */

public class PostCursorAdapter extends CursorAdapter {
    //instance variable
    private String user;

    //constructor
    public PostCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.user = user;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_post_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvBook = (TextView) view.findViewById(R.id.post_book);
        TextView tvCondition = (TextView) view.findViewById(R.id.post_condition);
        TextView tvPrice = (TextView) view.findViewById(R.id.post_price);
        TextView tvId = (TextView) view.findViewById(R.id.individual_post_id);
        // Extract properties from cursor
        String book = cursor.getString(cursor.getColumnIndexOrThrow("book_name"));
        String condition = cursor.getString(cursor.getColumnIndexOrThrow("condition"));
        Double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
        Integer postID = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        // Populate fields with extracted properties
            tvBook.setText(book);
            tvCondition.setText(condition);
            tvPrice.setText(String.valueOf(price));
            tvId.setText(String.valueOf(postID));
    }
}
