package com.example.altri;

import android.app.Activity;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseUser;

public class SettingsActivity extends Activity{


    private Button btnChat;
    public static final String TAG = "SettingsActivity";
    private Button btnChangeColor;
    private Button btnLogout;
    private Button btnProfile;

    private Button btnBlue;
    private Button btnYellow;
    private Button btnRed;

    private ImageButton btnBack;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        btnProfile = findViewById(R.id.btnProfile);
        btnChangeColor = findViewById(R.id.btnChangeColor);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.imageButton);

        Intent logoutIntent = new Intent(this, LoginSignUpActivity.class);
        Intent backIntent = new Intent(getApplicationContext(), MainMenuActivity.class);

        Intent profile = new Intent (this, ProfileActivity.class);
        Intent logout = new Intent(this, LoginSignUpActivity.class);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick back");
                startActivity(backIntent);
                finish();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(profile);
                finish();

                ParseUser currentUser = ParseUser.getCurrentUser();

                Toast.makeText(SettingsActivity.this, currentUser.getString("firstname") + "'s Profile", Toast.LENGTH_SHORT).show();
            }
        });

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
                startActivity(logoutIntent);
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
                //settingsView.setBackgroundResource(R.drawable.settings_yellow);
            }
        });

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPopupView.setBackgroundResource(R.drawable.colorpick_red);
            }
        });

    }

}