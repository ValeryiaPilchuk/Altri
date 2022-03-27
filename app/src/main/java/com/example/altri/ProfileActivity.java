package com.example.altri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

public class ProfileActivity extends Activity {

    public static final String TAG = "ProfileActivity";

    private Button btnFirstNameProfile;
    private Button btnLastNameProfile;
    private Button btnDateOfBirthProfile;
    private Button btnEmailProfile;
    private Button btnChangePassword;

    private Button btnDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        btnFirstNameProfile = findViewById(R.id.btnFirstNameProfile);
        btnLastNameProfile = findViewById(R.id.btnLastNameProfile);
        btnDateOfBirthProfile = findViewById(R.id.btnDateOfBirthProfile);
        btnEmailProfile = findViewById(R.id.btnEmailProfile);
        btnChangePassword = findViewById(R.id.btnChangePasswordProfile);

        //btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        ParseUser currentUser = ParseUser.getCurrentUser();

        btnFirstNameProfile.setText(currentUser.getString("firstname"));
        btnLastNameProfile.setText(currentUser.getString("lastname"));
        btnDateOfBirthProfile.setText(currentUser.getString("dateofbirth"));
        btnEmailProfile.setText(currentUser.getUsername());

        Intent changePassword = new Intent(ProfileActivity.this, ResetPasswordActivity.class);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Toast.makeText(ProfileActivity.this, "Email has been sent to change your password!", Toast.LENGTH_SHORT).show();
                    ParseUser.requestPasswordReset(btnEmailProfile.getText().toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                /*
                startActivity(changePassword);
                finish();

                 */
            }
        });

        /*
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteUser(btnEmailProfile.getText().toString());

            }
        });
         */

    }

    /*
    private void deleteUser(String username) {

        ParseUser currentUser = ParseUser.getCurrentUser();
        String objectID = currentUser.getString("objectId");

        System.out.print(objectID);

        /*
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    object.deleteInBackground();
                } else {
                    // something went wrong
                }
            }
        });
         */

        /*
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.getInBackground(objectID, new GetCallback<ParseObject> {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (e == null) {
                    object.deleteInBackground();
                    //object.saveInBackground();

                    Intent dashboard = new Intent(getApplicationContext(),LoginSignUpActivity.class);
                    Toast.makeText(getApplicationContext(), "Deleted Successuly.", Toast.LENGTH_SHORT).show();
                    startActivity(dashboard);

                } else {
                    e.printStackTrace();
                }

            }
        });

         */

        /*
        // Configure Query with our query.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");

        query.whereEqualTo("username", username);

        // on below line we are finding the course with the course name.
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                // if the error is null.
                if (e == null) {
                    // on below line we are getting the first course and
                    // calling a delete method to delete this course.
                    objects.get(0).deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            // inside done method checking if the error is null or not.
                            if (e == null) {
                                // if the error is not null then we are displaying a toast message and opening our home activity.
                                Toast.makeText(ProfileActivity.this, "Account deleted!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ProfileActivity.this, LoginSignUpActivity.class);
                                startActivity(i);
                            } else {
                                // if we get error we are displaying it in below line.
                                Toast.makeText(ProfileActivity.this, "Failed to delete account!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // if we don't get the data in our database then we are displaying below message.
                    Toast.makeText(ProfileActivity.this, "Fail to get the user", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    */
}
