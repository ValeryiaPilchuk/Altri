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

import com.example.altri.R;
import com.example.altri.Schedule;
import com.example.altri.SchedulerMenuActivity;
import com.example.altri.adapter.TaskAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;


public class TasksFragment extends Fragment {

    public static final String TAG = "Tasks";

    private RecyclerView tasksRV;
    private TextView dateTV;
    protected TaskAdapter adapter;
    private List<Schedule> allTasks;
    private TaskAdapter taskAdapter;
    private ImageButton btnBack;
    private Button btnviewAll;


    Date todaysDate = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public TasksFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return inflater.inflate(R.layout.activity_all_tasks_today, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_display_all_tasks);
        btnBack = view.findViewById(R.id.imageButton);
        tasksRV = view.findViewById(R.id.rv_messages);
        btnviewAll = view.findViewById(R.id.btnViewAll);
        TextView dateTV = (TextView) getView().findViewById(R.id.date_on_top);
        dateTV.setText(formatter.format(todaysDate));
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

    public void queryPosts() {
        String dateFromData = Schedule.KEY_TASK_DATE;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        Calendar calendar = Calendar.getInstance();
        /*
        char extra = '0';
        if (dateFromData.length()< 10){
            dateFromData = extra + dateFromData;
        }
        */
        ParseQuery<Schedule> query = ParseQuery.getQuery(Schedule.class);
        query.include(Schedule.KEY_USER);
        query.whereEqualTo(Schedule.KEY_TASK_DATE, formatter.format(todaysDate));
        query.whereGreaterThanOrEqualTo(Schedule.KEY_TASK_TIME_NUMBER, dateFormat.format(calendar.getTime()));
        query.whereEqualTo(Schedule.KEY_USER, ParseUser.getCurrentUser());
        query.orderByAscending(Schedule.KEY_TASK_TIME_NUMBER);
        query.setLimit(10);
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


