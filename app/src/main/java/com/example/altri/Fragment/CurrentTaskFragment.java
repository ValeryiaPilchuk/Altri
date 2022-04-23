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
import com.example.altri.CurrentTaskAdapter;
import com.parse.FindCallback;
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
    private List<Schedule> allTasks;
    private ImageButton btnBack;
    private TextView etTaskName;

    Date todaysDate = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public CurrentTaskFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_current_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent backIntent = new Intent(getApplicationContext(), SchedulerMenuActivity.class);

//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_display_all_tasks);
        etTaskName = view.findViewById(R.id.tvTaskName);
        btnBack = view.findViewById(R.id.btnBack);

        tasksRV = view.findViewById(R.id.rv_task);
 //       TextView dateTV = (TextView) getView().findViewById(R.id.date_on_top);
 //       dateTV.setText(formatter.format(todaysDate));
        allTasks = new ArrayList<>();
        adapter = new CurrentTaskAdapter(getContext(), allTasks); /*, new CurrentTaskAdapter(){
            @Override
            public void onChangeTime(int position) {
                Log.d(TAG, "iconTextViewOnClick at position "+position);

            }
            @Override
            public void onCompleted(int position) {
                Log.d(TAG, "iconTextViewOnClick at position "+position);
            }

        });
*/

        tasksRV.setAdapter(adapter);
        tasksRV.setLayoutManager(new LinearLayoutManager(getContext()));



        queryPosts();

//        String task = Integer.toString(allTasks.size());
//        etTaskName.setText(task);

        btnBack.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(backIntent);
            }
        });

    }


    public void queryPosts() {
        String dateFromData = Schedule.KEY_TASK_DATE;
        /*
        char extra = '0';
        if (dateFromData.length()< 10){
            dateFromData = extra + dateFromData;
        }
        */
        ParseQuery<Schedule> query = ParseQuery.getQuery(Schedule.class);
        query.include(Schedule.KEY_USER);
        query.whereEqualTo(Schedule.KEY_TASK_DATE, formatter.format(todaysDate));
        query.whereEqualTo(Schedule.KEY_USER, ParseUser.getCurrentUser());
        query.orderByDescending(Schedule.KEY_TASK_TIME);
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



