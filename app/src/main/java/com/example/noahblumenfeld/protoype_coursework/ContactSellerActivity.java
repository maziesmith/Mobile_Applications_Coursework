package com.example.noahblumenfeld.protoype_coursework;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Screen for inquiring the seller of the book about buying it
 * Created by Noah Blumenfeld
 */
public class ContactSellerActivity extends AppCompatActivity {

    //instance variables
    private String sellerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_seller);
        setTitle("Contact Seller");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            sellerEmail = extras.getString("SELLER_EMAIL");
        }

        final EditText subjectText = (EditText) findViewById(R.id.subject_text);
        final EditText messageText = (EditText) findViewById(R.id.email_body);

        Button sendEmailButton = (Button) findViewById(R.id.email_send);
        //send the email to the seller
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] to = {sellerEmail};
                String messageBody = messageText.getText().toString();
                String subject = subjectText.getText().toString();
                sendEmail(to,subject, messageBody);
            }
        });
    }

    /**
     * Method that changes the user's inputted text into text fields in the email and then sends it.
     */
    private void sendEmail(String[] emailAddresses, String subject, String body){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        String[] to = emailAddresses;
        String[] cc = {};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC,cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,body);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email"));

    }
}
