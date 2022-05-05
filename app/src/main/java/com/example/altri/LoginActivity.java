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
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.LogInCallback;

public class LoginActivity extends Activity {

    public static final String TAG = "LoginActivity";
    private Button btnLogin;
    private Button btnForgotPassword;

    private EditText etEmail;
    private EditText etPassword;

    private ImageButton btnBack;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            goRolePickActivity();
        }

        btnLogin = findViewById(R.id.Login);
        btnForgotPassword = findViewById(R.id.forgotPassword);

        etEmail = findViewById(R.id.EmailLogin);
        etPassword = findViewById(R.id.PasswordLogin);
        btnBack = findViewById(R.id.imageButton);

        Intent backIntent = new Intent(getApplicationContext(), LoginSignUpActivity.class);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick back");
                startActivity(backIntent);
                finish();
            }
        });

        Intent resetPassword = new Intent(LoginActivity.this, ResetPasswordActivity.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick Login Button");

                String username = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(etEmail.getText())) {
                    etEmail.setError("Email is required!");
                } else if (!(etEmail.getText().toString().trim().matches(emailPattern))) {
                    etEmail.setError("Email format incorrect!");
                } else if (TextUtils.isEmpty(etPassword.getText())) {
                    etPassword.setError("Password is required!");
                }

                loginUser(username, password);
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etEmail.getText())) {
                    etEmail.setError("Email is required!");
                } else if (!(etEmail.getText().toString().trim().matches(emailPattern))) {
                    etEmail.setError("Email format incorrect!");
                }

                    try {
                        Toast.makeText(LoginActivity.this, "Email has been sent to change your password!", Toast.LENGTH_SHORT).show();
                        ParseUser.requestPasswordReset(etEmail.getText().toString());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });

    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // If the request failed, the exception is not null
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();

                goRolePickActivity();
                Toast.makeText(LoginActivity.this, "Welcome Back " + currentUser.getString("firstname"), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goRolePickActivity() {
        Intent intent = new Intent(this, RolePickActivity.class);
        startActivity(intent);
        finish();
    }


}
