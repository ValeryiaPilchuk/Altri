package com.example.altri;

import static android.media.CamcorderProfile.get;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.altri.Fragment.TasksFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CurrentTaskActivity extends Activity {

    public static final String TAG = "CurrentTaskActivity";

    private TextView etTaskName;
    protected TaskAdapter adapter;
    private List<Schedule> allTasks;

    Date todaysDate = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_current_task);

        etTaskName = findViewById(R.id.tvTaskName);

        Schedule schedule = new Schedule();

        allTasks = new ArrayList<>();
        adapter = new TaskAdapter(getApplicationContext(), allTasks);

        queryPosts();

        String task = Integer.toString(allTasks.size());
        etTaskName.setText(task);



  //      TasksFragment tasks = new TasksFragment();
  //      tasks.queryPosts();
    }


    public void queryPosts() {
        String dateFromData = Schedule.KEY_TASK_DATE;
        ParseQuery<Schedule> query = ParseQuery.getQuery(Schedule.class);
        query.include(Schedule.KEY_USER);
        query.setLimit(20);
        query.whereEqualTo(Schedule.KEY_TASK_DATE, formatter.format(todaysDate));
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
