package com.example.altri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.altri.Fragment.CurrentTaskFragment;
import com.example.altri.Fragment.MessageFragment;
import com.example.altri.Fragment.TasksFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragmentTask;
        Fragment fragmentCurrent;
        Fragment fragmentMessage;
        fragmentTask = new TasksFragment();
        fragmentCurrent = new CurrentTaskFragment();
        fragmentMessage = new MessageFragment();


        String task = getIntent().getStringExtra("Task");

        if (task.equals("current")){
            fragmentManager.beginTransaction().replace(R.id.flFragment, fragmentCurrent).commit();
        }
        if (task.equals("edit")){
            fragmentManager.beginTransaction().replace(R.id.flFragment, fragmentTask).commit();
        }
        if (task.equals("completed")){
            fragmentManager.beginTransaction().replace(R.id.flFragment, fragmentMessage).commit();
        }





        /*
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new TasksFragment();
                        break;
                    case R.id.action_bot:
                        fragment = new ChatbotMainMenu();
                        break;
                    case R.id.action_scheduler:
                    default:
                        fragment = new TasksFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        */
    }
}