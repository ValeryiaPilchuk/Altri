package com.example.altri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.SignUpCallback;

import androidx.annotation.Nullable;

public class CreateAccountActivity extends Activity {

    public static final String TAG = "CreateAccountActivity";
    private Button createAccount;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etDateofBirth;
    private EditText etEmail;
    private EditText etPassword;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser.getCurrentUser().logOut();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_account);

        createAccount = findViewById(R.id.CreateAccount);

        etFirstName = findViewById(R.id.FirstName);
        etLastName = findViewById(R.id.LastName);
        etDateofBirth = findViewById(R.id.DateofBirth);
        etEmail = findViewById(R.id.EmailSignup);
        etPassword = findViewById(R.id.PasswordSignup);

        Intent newAccount = new Intent(this, RolePickActivity.class);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etFirstName.getText())){
                    etFirstName.setError("First Name is required!");
                }
                else if (TextUtils.isEmpty(etLastName.getText())){
                    etLastName.setError("Last Name is required!");
                }
                else if (TextUtils.isEmpty(etDateofBirth.getText())){
                    etDateofBirth.setError("Date of Birth is required!");
                }
                else if (TextUtils.isEmpty(etEmail.getText())){
                    etEmail.setError("Email is required!");
                }
                else if (!(etEmail.getText().toString().trim().matches(emailPattern))) {
                    etEmail.setError("Email format incorrect!");
                }
                else if (TextUtils.isEmpty(etPassword.getText())){
                    etPassword.setError("Password is required!");
                }

                /*
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String dateofbirth = etDateofBirth.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                */

                // calling method to add data to Firebase Firestore.
                //addDataToDatabase(firstName, lastName, dateofbirth, email, password);

                ParseUser user = new ParseUser();

                user.put("firstname",etFirstName.getText().toString());
                user.put("lastname",etLastName.getText().toString());
                user.setUsername(etEmail.getText().toString());
                user.setPassword(etPassword.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Log.e(TAG, "Issue with SignUp", e);
                            Toast.makeText(CreateAccountActivity.this, "Issue with Sign Up!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(CreateAccountActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(newAccount);
                        finish();
                    }
                });

                //Log.i(TAG, "onClick create account button");
                //startActivity(newAccount);
                //finish();
            }
        });

    }

    /*
    private void addDataToDatabase(String firstName, String lastName, String dateofbirth, String email, String password) {

        ParseObject user = new ParseObject("User");

        user.put("firstname", firstName);
        user.put("lastname", lastName);
        user.put("dateofbirth", dateofbirth);
        user.put("email", email);
        user.put("password", password);

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with SignUp", e);
                    Toast.makeText(CreateAccountActivity.this, "Issue with SignUp!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(CreateAccountActivity.this, "SignUp Successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }

     */
}
