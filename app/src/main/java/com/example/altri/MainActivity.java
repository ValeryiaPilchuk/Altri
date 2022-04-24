package com.example.altri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.altri.Fragment.AllTasksFragment;
import com.example.altri.Fragment.ChatbotMainMenu;
import com.example.altri.Fragment.CurrentTaskFragment;
import com.example.altri.Fragment.TasksFragment;
import com.example.altri.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

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
        Fragment fragmentAllTasks;
        fragmentTask = new TasksFragment();
        fragmentCurrent = new CurrentTaskFragment();
        fragmentAllTasks = new AllTasksFragment();


        String task = getIntent().getStringExtra("Task");

        if (task.equals("current")){
            fragmentManager.beginTransaction().replace(R.id.flFragment, fragmentCurrent).commit();
        }
        if (task.equals("edit")){
            fragmentManager.beginTransaction().replace(R.id.flFragment, fragmentTask).commit();
        }
        if (task.equals("all")){
            fragmentManager.beginTransaction().replace(R.id.flFragment, fragmentAllTasks).commit();
        }
    }
}