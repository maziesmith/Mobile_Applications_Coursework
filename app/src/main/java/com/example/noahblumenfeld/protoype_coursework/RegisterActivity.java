package com.example.noahblumenfeld.protoype_coursework;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;

/**
 * An screen for allowing new users to register based on the college they choose and are a student at
 * Created by Noah Blumenfeld
 */

public class RegisterActivity extends AppCompatActivity {

    //class instance variables
    private EditText email, password;
    private String F_NAME, L_NAME, EMAIL, PASSWORD, COLLEGE, emailErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        final EditText fName = (EditText) findViewById(R.id.Ret1);
        final EditText lName = (EditText) findViewById(R.id.Ret2);
        email = (EditText) findViewById(R.id.Ret3);
        password = (EditText) findViewById(R.id.Ret4);
        final Spinner college = (Spinner) findViewById(R.id.colleges);

        Button completeRegistrationButton = (Button) findViewById(R.id.registration_complete);
        completeRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F_NAME = fName.getText().toString();
                L_NAME = lName.getText().toString();
                EMAIL = email.getText().toString();
                PASSWORD = password.getText().toString();
                COLLEGE = college.getSelectedItem().toString();
                if(attemptRegistration()){
                    /*
                    All information fields were correct, allow user to register information
                    into the database and bring them back to the login screen.
                     */
                    register();
                    Toast.makeText(RegisterActivity.this, "You registered!", Toast.LENGTH_LONG).show();
                    Intent loginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(loginActivity);
                }


            }
        });
    }

    /**
     * Method to check if the user inputted information into the fields correctly
     *
     */
    private boolean attemptRegistration(){
        //email and password were find
        if(checkEmail(COLLEGE) && checkPassword())
            return true;
        //email was entered wrong
        else if(!checkEmail(COLLEGE) && checkPassword()) {
            email.setError(emailErrorMessage);
            return false;
        }
        //email was too short
        else if(!checkPassword() && checkEmail(COLLEGE)){
            password.setError("Password must be at least 6 characters long");
            return false;
        }
        //email and password were entered wrong
        else {
            email.setError(emailErrorMessage);
            password.setError("Password must be at least 6 characters long");
            return false;
        }
    }

    /**
     * Checks to make sure email is valid with the chosen university
     */
    private boolean checkEmail(String college){
        DBHandler dbHandler = new DBHandler(RegisterActivity.this);
        if(!dbHandler.checkExists(EMAIL)) {
//            Log.d("Check",dbHandler.checkExists(EMAIL).toString());
            if (EMAIL.contains(dbHandler.checkEmail(college)))
                return true;
            else{
                emailErrorMessage = "Email must match the chosen university";
                return false;
            }
        }
        else{
            emailErrorMessage = "Email has already been registered";
            return false;
        }
    }

    /**
     * Checks the length of the password to ensure it is at least 6 characters long
     *
     */
    private boolean checkPassword(){
        if(PASSWORD.length() >= 6)
            return true;
        else
            return false;
    }

    /**
     * Creates a new user within the database
     */
    private void register() {
        UserRegisterTask rUser = new UserRegisterTask(F_NAME, L_NAME, EMAIL, PASSWORD, COLLEGE);
        rUser.doInBackground();
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        //class instance variables
        private final String mfName;
        private final String mlName;
        private final String mEmail;
        private final String mPassword;
        private final String mCollege;

        UserRegisterTask(String fName, String lName, String email, String password, String college) {
            mfName = fName;
            mlName = lName;
            mEmail = email;
            mPassword = password;
            mCollege = college;
        }

        //Registers the user into the database
        @Override
        protected Boolean doInBackground(Void... params) {
            DBHandler dbHandler = new DBHandler(getBaseContext());
            User nUser = new User(null,mfName,mlName,mEmail,mPassword,mCollege);
            try {
                dbHandler.registerUser(nUser);
            } finally{
                return true;
            }
        }
    }
}
