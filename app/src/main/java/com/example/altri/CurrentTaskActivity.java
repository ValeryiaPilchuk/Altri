package com.example.altri;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.parse.ParseUser;

public class CurrentTaskActivity extends Activity {

    public static final String TAG = "CurrentTaskActivity";

    private TextView etTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_current_task);

        etTaskName = findViewById(R.id.tvTaskName);

        Schedule schedule = new Schedule();

        //etTaskName.setText("yees");

        etTaskName.setText(schedule.getTaskName());

    }



}
