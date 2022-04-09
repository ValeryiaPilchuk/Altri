package com.example.altri;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends Activity {

    public static final String TAG = "AddTaskActivity";
    private EditText etTaskName;
    private EditText etTaskDescription;
    private Button btnTaskDate;
    private Button btnTaskTime;
    private Button btnTaskImage;
    private Button btnAddTask;
    private ImageButton btnBack;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_task);

        etTaskName = findViewById(R.id.etTaskName);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        btnBack = findViewById(R.id.imageButton);

        btnTaskDate = findViewById(R.id.btnTaskDate);
        btnTaskTime = findViewById(R.id.btnTaskTime);
        btnTaskImage = findViewById(R.id.btnTaskImage);
        btnAddTask = findViewById(R.id.btnAddTask);

        Intent backIntent = new Intent(getApplicationContext(), SchedulerMenuActivity.class);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick back");
                startActivity(backIntent);
                finish();
            }
        });

        btnTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                btnTaskDate.setText(date);
            }
        };

        btnTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mTimeSetListener, 12, 0, false);

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
                    btnTaskTime.setText(display12hours.format(date));
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }


            }
        };

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseUser currentUser = ParseUser.getCurrentUser();

                saveSchedule(etTaskName.getText().toString(), etTaskDescription.getText().toString(), btnTaskDate.getText().toString(), btnTaskTime.getText().toString(), currentUser);

            }
        });

        /*
        Spinner spinner = (Spinner) findViewById(R.id.spnTaskRepeat);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.repeat_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        */
    }

    private void saveSchedule(String taskName, String taskDescription, String taskDate, String taskTime, ParseUser currentUser) {
        Schedule schedule = new Schedule();

        schedule.setTaskName(taskName);
        schedule.setTaskDescription(taskDescription);
        schedule.setTaskDate(taskDate);
        schedule.setTaskTime(taskTime);
        schedule.setUser(currentUser);

        schedule.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "error", e);
                    Toast.makeText(AddTaskActivity.this, "Failed to add task", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTaskActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
                    etTaskName.setText("");
                    etTaskDescription.setText("");
                    btnTaskDate.setText("");
                    btnTaskTime.setText("");
                }
            }
        });

    }

}