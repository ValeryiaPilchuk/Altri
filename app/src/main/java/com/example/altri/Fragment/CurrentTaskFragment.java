package com.example.altri.Fragment;

import static com.parse.Parse.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altri.Main;
import com.example.altri.R;
import com.example.altri.Schedule;
import com.example.altri.SchedulerMenuActivity;
import com.example.altri.adapter.CurrentTaskAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;


public class CurrentTaskFragment extends Fragment {

    public static final String TAG = "Tasks";

    private RecyclerView tasksRV;
    protected CurrentTaskAdapter adapter;
    private List<Schedule> currentTask;
    private ImageButton btnBack;
    private Button btnCompleted;
    private TextView etTaskName;

    Date todaysDate = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public CurrentTaskFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.activity_current_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent backIntent = new Intent(getApplicationContext(), SchedulerMenuActivity.class);

        btnBack = view.findViewById(R.id.btnBack);
        btnCompleted = view.findViewById(R.id.btnCompleted);
        tasksRV = view.findViewById(R.id.rv_task);
        currentTask = new ArrayList<>();
        adapter = new CurrentTaskAdapter(getContext(), currentTask);
        tasksRV.setAdapter(adapter);
        tasksRV.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();

        btnBack.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(backIntent);
            }
        });

    }

    public void queryPosts() {
        String dateFromData = Schedule.KEY_TASK_DATE;

        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        Calendar calendar = Calendar.getInstance();

        System.out.println(dateFormat.format(calendar.getTime()));

        ParseQuery<Schedule> query = ParseQuery.getQuery(Schedule.class);
        query.include(Schedule.KEY_USER);
        query.orderByAscending(Schedule.KEY_TASK_TIME_NUMBER);
        query.whereEqualTo(Schedule.KEY_TASK_DATE, formatter.format(todaysDate));
        query.whereEqualTo(Schedule.KEY_USER, ParseUser.getCurrentUser());
        query.whereGreaterThanOrEqualTo(Schedule.KEY_TASK_TIME_NUMBER, dateFormat.format(calendar.getTime()));
        query.whereEqualTo(Schedule.KEY_TASK_COMPLETED, "no");

        query.getFirstInBackground(new GetCallback<Schedule>() {
            @Override
            public void done(Schedule task, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issues with getting task", e);
                    return;
                }

                currentTask.add(task);
                adapter.notifyDataSetChanged();

            }
        });

    }

}


