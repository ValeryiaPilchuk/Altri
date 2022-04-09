package com.example.altri.Fragment;

import static com.parse.Parse.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altri.R;
import com.example.altri.Schedule;
import com.example.altri.SchedulerMenuActivity;
import com.example.altri.TaskAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;


public class AllTasks extends Fragment {

    public static final String TAG = "Tasks";

    private RecyclerView tasksRV;
    protected TaskAdapter adapter;
    private List<Schedule> allTasks;
    private TaskAdapter taskAdapter;
    private ImageButton btnBack;


    public AllTasks(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_all_tasks_today, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_display_all_tasks);
        btnBack = view.findViewById(R.id.imageButton);
        allTasks = new ArrayList<>();
        adapter = new TaskAdapter(getContext(), allTasks);

        tasksRV.setAdapter(adapter);
        tasksRV.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();

        Intent backIntent = new Intent(getApplicationContext(), SchedulerMenuActivity.class);

        btnBack.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(backIntent);
            }
        });

    }

    protected void queryPosts() {
        String dateFromData = Schedule.KEY_TASK_DATE;
        /*
        char extra = '0';
        if (dateFromData.length()< 10){
            dateFromData = extra + dateFromData;
        }
        */
        ParseQuery<Schedule> query = ParseQuery.getQuery(Schedule.class);
        query.include(Schedule.KEY_USER);
        query.setLimit(30);
        query.whereEqualTo(Schedule.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Schedule.KEY_TASK_TIME);
        query.findInBackground(new FindCallback<Schedule>() {
            @Override
            public void done(List<Schedule> tasks, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issues with getting posts", e);
                    return;
                }
                for (Schedule task: tasks){
                    //TODO: add correct logging later
                    //Log.i(TAG, "Post: " + task.getDescription()+"username: " + task.getUser().getUsername());
                }

                allTasks.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
    }

}



