package com.example.altri;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.altri.Fragment.TasksFragment;

public class SchedulerMenuActivity extends Activity {

    public static final String TAG = "TaskMenuActivity";
    private Button btnAddTask;
    private Button btnEditTask;
    private Button btnCurrentTask;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_menu);

        btnAddTask = findViewById(R.id.btnAddTask);
        btnEditTask = findViewById(R.id.btnEditTask);
        btnCurrentTask = findViewById(R.id.btnCurrentTask);
        btnBack = findViewById(R.id.imageButton);

        Intent addTask = new Intent(this, AddTaskActivity.class);
        Intent editTask = new Intent(this, MainActivity.class);
        Intent currentTask = new Intent(this, CurrentTaskActivity.class);
        Intent backIntent = new Intent(getApplicationContext(), MainMenuActivity.class);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick back");
                startActivity(backIntent);
                finish();
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(addTask);
                finish();
            }
        });

        btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(editTask);
                finish();
            }
        });

        btnCurrentTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(currentTask);
                finish();
            }
        });
    }
}