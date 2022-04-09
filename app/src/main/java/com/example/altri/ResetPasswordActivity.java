package com.example.altri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.ParseException;

public class ResetPasswordActivity extends Activity {

    public static final String TAG = "ResetPasswordActivity";

    private EditText etEmailReset;

    private Button btnSendResetLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset_password);

        etEmailReset = findViewById(R.id.etEmailReset);

        btnSendResetLink = findViewById(R.id.btnSendResetLink);

        //Intent profile = new Intent(ResetPasswordActivity.this, ProfileActivity.class);

        btnSendResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Toast.makeText(ResetPasswordActivity.this, "Email has been sent to change your password!", Toast.LENGTH_SHORT).show();
                    ParseUser.requestPasswordReset(etEmailReset.getText().toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                /*
                if (TextUtils.isEmpty(etNewPassword.getText())) {
                    etNewPassword.setError("Please enter your new password!");
                } else if (TextUtils.isEmpty(etNewPasswordAgain.getText())) {
                    etNewPasswordAgain.setError("Please enter your new password again!");
                } else if (!(etNewPassword.getText().toString().trim().matches(etNewPasswordAgain.getText().toString()))) {
                    etNewPasswordAgain.setError("New passwords do not match!");
                } else if (TextUtils.isEmpty(etCurrentPassword.getText())) {
                    etCurrentPassword.setError("Your current password is required!");
                } else if (!(etCurrentPassword.getText().toString().trim().matches((Objects.requireNonNull(currentUser.getString("password")))))) {
                    etCurrentPassword.setError("Your current password is wrong!");
                } else {
                   savePassword(etNewPassword.getText().toString(), currentUser);

                }

                 */


                /*
                else if (!(etNewPassword.getText().toString().trim().matches(currentUser.getString("password")))){
                    etNewPassword.setError("Your new password is same as your current password!");
                    etNewPasswordAgain.setError("Your new password is same as your current password!");
                }
                else {

                }

                /*
                currentUser.remove("password");


                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "error", e);
                            Toast.makeText(ChangePasswordActivity.this, "Failed to chang password", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            currentUser.put("password", etNewPassword.getText().toString());
                            Toast.makeText(ChangePasswordActivity.this, "Password changed!", Toast.LENGTH_SHORT).show();
                            startActivity(profile);
                        }
                    }
                });
                */

                /*
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String dateofbirth = etDateofBirth.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                */

                // calling method to add data to Firebase Firestore.
                //addDataToDatabase(firstName, lastName, dateofbirth, email, password);

                /*
                ParseUser user = new ParseUser();

                user.put("firstname", etFirstName.getText().toString());
                user.put("lastname", etLastName.getText().toString());
                user.put("dateofbirth", btnDateOfBirth.getText().toString());
                user.setUsername(etEmail.getText().toString());
                user.setPassword(etPassword.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Issue with SignUp", e);
                            Toast.makeText(CreateAccountActivity.this, "Issue with Sign Up!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(CreateAccountActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(newAccount);
                        finish();
                    }
                });
                */

            }
        });
    }

    /*
    private void savePassword(String newPassword, ParseUser currentUser) {

        currentUser.put("password", newPassword);

        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                Intent profile = new Intent(ChangePasswordActivity.this, ProfileActivity.class);

                if (e != null) {
                    Log.e(TAG, "error", e);
                    Toast.makeText(ChangePasswordActivity.this, "Failed to change password!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ChangePasswordActivity.this, "Password changed!", Toast.LENGTH_SHORT).show();
                    startActivity(profile);
                    finish();
                }
            }
        });
    }
     */
}