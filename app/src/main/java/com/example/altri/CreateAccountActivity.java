package com.example.altri;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.SignUpCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Calendar;

public class CreateAccountActivity extends Activity {

    public static final String TAG = "CreateAccountActivity";
    private Button createAccount;


    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPassword;

    private Button btnDateOfBirth;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_account);



        etFirstName = findViewById(R.id.FirstName);
        etLastName = findViewById(R.id.LastName);
        etEmail = findViewById(R.id.EmailSignup);
        etPassword = findViewById(R.id.PasswordSignup);
        btnDateOfBirth = findViewById(R.id.btnDateOfBirth);
        createAccount = findViewById(R.id.CreateAccount);

        Intent newAccount = new Intent(this, RolePickActivity.class);

        btnDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateAccountActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                btnDateOfBirth.setText(date);
            }
        };

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etFirstName.getText())){
                    etFirstName.setError("First Name is required!");
                }
                else if (TextUtils.isEmpty(etLastName.getText())){
                    etLastName.setError("Last Name is required!");
                }
                else if (TextUtils.isEmpty(btnDateOfBirth.getText())){
                    btnDateOfBirth.setError("Date of Birth is required!");
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



                ParseUser user = new ParseUser();

                user.put("firstname",etFirstName.getText().toString());
                user.put("lastname",etLastName.getText().toString());
                user.put("dateofbirth",btnDateOfBirth.getText().toString());
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
}
