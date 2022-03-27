package com.example.altri;

import android.app.Activity;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

public class SettingsActivity extends Activity{

        public static final String TAG = "RolePickActivity";
        private Button btnChat;
    public static final String TAG = "TaskMenuActivity";
    private Button btnChangePassword;
    private Button btnChangeColor;
    private Button btnLogout;

    private Button btnBlue;
    private Button btnYellow;
    private Button btnRed;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        btnChat = findViewById(R.id.btnChat);

        Intent Chat = new Intent(this, ChatbotActivity.class); //move from here to chat

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                startActivity(Chat);
                finish();
            }
        });

        btnChangeColor = findViewById(R.id.btnChangeColor);
        btnLogout = findViewById(R.id.btnLogout);

        Intent logout = new Intent(this, LoginSignUpActivity.class);

        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeColor();

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();

                Toast.makeText(SettingsActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
                startActivity(logout);
                finish();

            }
        });

    }

    private void changeColor(){

        dialogBuilder = new AlertDialog.Builder(this);
        final View colorPopupView = getLayoutInflater().inflate(R.layout.popup_color_change, null);

        final View settingsView = getLayoutInflater().inflate(R.layout.activity_settings, null);
        //View background = findViewById(R.layout.activity_settings);

        dialogBuilder.setView(colorPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnBlue = colorPopupView.findViewById(R.id.btnBlue);
        btnYellow = colorPopupView.findViewById(R.id.btnYellow);
        btnRed = colorPopupView.findViewById(R.id.btnRed);

        Intent settings = new Intent(this, SettingsActivity.class);

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPopupView.setBackgroundResource(R.drawable.colorpick_blue);
            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPopupView.setBackgroundResource(R.drawable.colorpick_yellow);
                settingsView.setBackgroundResource(R.drawable.settings_yellow);
            }
        });

    }

}