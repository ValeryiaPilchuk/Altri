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

public class MainMenuActivity extends Activity {

    private Button btnChat;
    private Button btnScheduler;
    private Button btnSettings;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        btnChat = findViewById(R.id.btnChat);
        btnScheduler = findViewById(R.id.btnScheduler);
        btnSettings = findViewById(R.id.btnSettings);
        btnBack = findViewById(R.id.imageButton);

        Intent settings = new Intent(this, SettingsActivity.class);
        Intent task = new Intent(this, SchedulerMenuActivity.class);
        Intent chat = new Intent(this, ChatbotMainMenuActivity.class);
        Intent backIntent = new Intent(getApplicationContext(), RolePickActivity.class);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick back");
                startActivity(backIntent);
                finish();
            }
        });

        btnScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(task);
                finish();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(settings);
                finish();
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(chat);
                finish();
            }
        });
    }

}

