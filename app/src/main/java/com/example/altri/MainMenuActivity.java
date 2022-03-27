package com.example.altri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

public class MainMenuActivity extends Activity {

    private Button btnChat;
    private Button btnScheduler;
    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        btnChat = findViewById(R.id.btnChat);
        btnScheduler = findViewById(R.id.btnScheduler);
        btnSettings = findViewById(R.id.btnSettings);

        Intent settings = new Intent(this, SettingsActivity.class);
        Intent task = new Intent(this, TaskMenuActivity.class);
        Intent Chat = new Intent(this, ChatbotActivity.class); //move from here to chat

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Chat);
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
    }

}

