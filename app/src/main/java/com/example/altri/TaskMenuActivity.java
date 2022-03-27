package com.example.altri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.altri.Fragment.TasksFragment;

public class TaskMenuActivity extends Activity{

        public static final String TAG = "TaskMenuActivity";
        private Button btnAddTask;
        private Button btnEditTask;
        private Button btnCurrentTask;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_task_menu);

            btnAddTask = findViewById(R.id.btnAddTask);
            btnEditTask = findViewById(R.id.btnEditTask);
            btnCurrentTask = findViewById(R.id.btnCurrentTask);

            Intent addTask = new Intent(this, AddTaskActivity.class);
            Intent editTask = new Intent(this, MainActivity.class);

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
        }
}