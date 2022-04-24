package com.example.altri;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.OpenableColumns;
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

import com.github.dhaval2404.imagepicker.ImagePicker;
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
    private Button btnTaskStartTime;
    private Button btnTaskImageVideo;
    private Button btnRepeat;
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
        btnTaskStartTime = findViewById(R.id.btnTaskStartTime);
        btnTaskImageVideo = findViewById(R.id.btnTaskImageVideo);
        btnRepeat = findViewById(R.id.btnRepeat);
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

        btnTaskImageVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddTaskActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

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

                if (month < 10) {
                    String date = "0" + month + "/" + day + "/" + year;
                    btnTaskDate.setText(date);
                }

                else {
                    String date = month + "/" + day + "/" + year;
                    btnTaskDate.setText(date);
                }

            }
        };

        btnTaskStartTime.setOnClickListener(new View.OnClickListener() {
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
                    btnTaskStartTime.setText(display12hours.format(date));
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }


            }
        };

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] fonts = {"Every Day", "Every Week", "Every Month", "Every Year"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddTaskActivity.this);
                builder.setTitle("Select how often to repeat the task");
                builder.setItems(fonts, new DialogInterface.OnClickListener() {@
                        Override
                public void onClick(DialogInterface dialog, int which) {
                    if ("Every Day".equals(fonts[which])) {
                        btnRepeat.setText("Every Day");
                        //Toast.makeText(AddTaskActivity.this, "you nailed it", Toast.LENGTH_SHORT).show();
                    } else if ("Every Week".equals(fonts[which])) {
                        btnRepeat.setText("Every Week");
                        //Toast.makeText(AddTaskActivity.this, "you cracked it", Toast.LENGTH_SHORT).show();
                    } else if ("Every Month".equals(fonts[which])) {
                        btnRepeat.setText("Every Month");
                        //Toast.makeText(AddTaskActivity.this, "you hacked it", Toast.LENGTH_SHORT).show();
                    } else if ("Every Year".equals(fonts[which])) {
                        btnRepeat.setText("Every Year");
                        //Toast.makeText(AddTaskActivity.this, "you digged it", Toast.LENGTH_SHORT).show();
                    }
                    // the user clicked on colors[which]
                }
                });
                builder.show();
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseUser currentUser = ParseUser.getCurrentUser();

                saveSchedule(etTaskName.getText().toString(), etTaskDescription.getText().toString(), btnTaskDate.getText().toString(), btnTaskStartTime.getText().toString(), currentUser);

                /*
                if (btnRepeat.getText().toString().equals("Every Day")) {


                    saveSchedule(etTaskName.getText().toString(), etTaskDescription.getText().toString(), btnTaskDate.getText().toString(), btnTaskStartTime.getText().toString(), currentUser);

                }

                 */

            }
        });

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
                    btnTaskStartTime.setText("");
                }
            }
        });

    }

}