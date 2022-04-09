package com.example.altri;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ChatbotMenu extends AppCompatActivity {

    private ImageButton btnBack;
    private Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chatbot_menu);

        btnBack = findViewById(R.id.imageButton);
        chat = findViewById(R.id.text_to_altri);

        Intent chatbotIntent = new Intent(this, ChatbotActivity.class);
        Intent backIntent = new Intent(getApplicationContext(), ChatbotMainMenuActivity.class);


        btnBack.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(backIntent);
                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick screen");
                startActivity(chatbotIntent);
                finish();
            }
        });
    }
}
