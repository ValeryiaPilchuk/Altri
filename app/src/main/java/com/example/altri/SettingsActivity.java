package com.example.altri;

import android.app.Activity;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

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
    private Button btnGreen;

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

        View settingsView;
        View mainmenuView;

        settingsView = findViewById(R.id.settings_layout);
        mainmenuView = findViewById(R.id.main_menu_layout);

        dialogBuilder = new AlertDialog.Builder(this);
        final View colorPopupView = getLayoutInflater().inflate(R.layout.popup_color_change, null);

        dialogBuilder.setView(colorPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnBlue = colorPopupView.findViewById(R.id.btnBlue);
        btnYellow = colorPopupView.findViewById(R.id.btnYellow);
        btnRed = colorPopupView.findViewById(R.id.btnRed);
        btnGreen = colorPopupView.findViewById(R.id.btnGreen);

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPopupView.setBackgroundResource(R.drawable.colorpick_blue);

                settingsView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.settings_blue));
                btnProfile.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blue_button));
                btnProfile.setTextColor(Color.parseColor("#18A1CC"));
                btnChangeColor.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blue_button));
                btnChangeColor.setTextColor(Color.parseColor("#18A1CC"));
                btnLogout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blue_button));
                btnLogout.setTextColor(Color.parseColor("#18A1CC"));
                //dialog.dismiss();
            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPopupView.setBackgroundResource(R.drawable.colorpick_yellow);

                settingsView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.settings_yellow));
                btnProfile.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.yellow_button));
                btnProfile.setTextColor(Color.parseColor("#CC6E18"));
                btnChangeColor.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.yellow_button));
                btnChangeColor.setTextColor(Color.parseColor("#CC6E18"));
                btnLogout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.yellow_button));
                btnLogout.setTextColor(Color.parseColor("#CC6E18"));

                //settings.setBackgroundResource(R.drawable.colorpick_yellow);
                //dialog.dismiss();
                //settingsView.setBackgroundResource(R.drawable.settings_yellow);
            }
        });

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPopupView.setBackgroundResource(R.drawable.colorpick_red);

                settingsView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.settings_red));
                btnProfile.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_button));
                btnProfile.setTextColor(Color.parseColor("#CC184E"));
                btnChangeColor.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_button));
                btnChangeColor.setTextColor(Color.parseColor("#CC184E"));
                btnLogout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_button));
                btnLogout.setTextColor(Color.parseColor("#CC184E"));
                //dialog.dismiss();
            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPopupView.setBackgroundResource(R.drawable.colorpick_green);

                settingsView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.settings_green));
                btnProfile.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.green_button));
                btnProfile.setTextColor(Color.parseColor("#009721"));
                btnChangeColor.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.green_button));
                btnChangeColor.setTextColor(Color.parseColor("#009721"));
                btnLogout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.green_button));
                btnLogout.setTextColor(Color.parseColor("#009721"));
                //dialog.dismiss();
            }
        });

    }

}