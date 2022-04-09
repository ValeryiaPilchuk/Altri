package com.example.altri;

import static com.example.altri.MainActivity.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class RolePickActivity extends Activity {

    public static final String TAG = "RolePickActivity";
    private Button btnCaregiver;
    private Button btnEmployer;
    private Button btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_role_pick);

        btnCaregiver = findViewById(R.id.btnCaregiver);
        btnUser = findViewById(R.id.btnUser);
        btnEmployer = findViewById(R.id.btnEmployer);

        Intent scheduler = new Intent(this, MainMenuActivity.class);
        Intent taskMenu = new Intent(this, SchedulerMenuActivity.class);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                startActivity(scheduler);
                finish();
            }
        });

        btnEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                startActivity(scheduler);
                finish();
            }
        });
        btnCaregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                startActivity(taskMenu);
                finish();
            }
        });
    }
}
