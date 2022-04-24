package com.example.altri;

import static android.media.CamcorderProfile.get;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

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
    protected TaskAdapter adapter;
    private List<Schedule> allTasks;

    Date todaysDate = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    private TextView etTaskName;
    private ImageView ivTaskImage;

    private Button btnCompleted;
    private Button btnChangeTime;

    private ImageButton btnBack;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_current_task);

        etTaskName = findViewById(R.id.tvTaskName);
        ivTaskImage = findViewById(R.id.ivTaskImage);

        ivTaskImage.setImageResource(R.drawable.empty_media);
        //ivTaskImage.setImageDrawable(R.drawable.empty_media);

        btnBack = findViewById(R.id.imageButton);

        btnCompleted = findViewById(R.id.btnCompleted);
        btnChangeTime = findViewById(R.id.btnChangeTime);

        Schedule schedule = new Schedule();

        allTasks = new ArrayList<>();
        adapter = new TaskAdapter(getApplicationContext(), allTasks);

        queryPosts();

        String task = Integer.toString(allTasks.size());
        etTaskName.setText(task);


        etTaskName.setText(schedule.getTaskName());

        Intent backIntent = new Intent(getApplicationContext(), SchedulerMenuActivity.class);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick back");
                startActivity(backIntent);
                finish();
            }
        });

        etTaskName.setText("Eat Breakfast");

        dialogBuilder = new AlertDialog.Builder(this);
        final View messagePopupView = getLayoutInflater().inflate(R.layout.popup_message, null);

        btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogBuilder.setView(messagePopupView);
                dialog = dialogBuilder.create();
                dialog.show();

                etTaskName.setText("No Current Task");

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
        });

        btnChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(CurrentTaskActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mTimeSetListener, 12, 0, false);

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();

            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                int hour = hourOfDay;
                int min = minute;

                String time = hour + ":" + min;

                SimpleDateFormat display24hours = new SimpleDateFormat("HH:mm");

                try {
                    Date date = display24hours.parse(time);
                    SimpleDateFormat display12hours = new SimpleDateFormat("hh:mm aa");
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }


            }
        };

    }

}
