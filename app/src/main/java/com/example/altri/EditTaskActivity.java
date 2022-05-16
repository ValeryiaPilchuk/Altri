package com.example.altri;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import static com.parse.Parse.getApplicationContext;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditTaskActivity extends Activity {

    public static final String TAG = "AddTaskActivity";
    private EditText etTaskName;
    private EditText etTaskDescription;
    private Button btnTaskDate;
    private Button btnTaskStartTime;
    private Button btnTaskImageVideo;
    private Button btnRepeat;
    private Button btnAddTask;
    private Button btnDeleteTask;
    private ImageButton btnBack;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    private String objectID;


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_task);

        String name = getIntent().getStringExtra("name");
        String time = getIntent().getStringExtra("time");
        String date = getIntent().getStringExtra("date");
        String description = getIntent().getStringExtra("description");


        etTaskName = findViewById(R.id.etTaskName);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);
        btnBack = findViewById(R.id.imageButton);
        btnTaskDate = findViewById(R.id.btnTaskDate);
        btnTaskStartTime = findViewById(R.id.btnTaskStartTime);
        btnTaskImageVideo = findViewById(R.id.btnTaskImageVideo);
        btnRepeat = findViewById(R.id.btnRepeat);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);

        if(name != null && time != null && date != null){
            etTaskName.setText(name);
            btnTaskStartTime.setText(time);
            btnTaskDate.setText(date);
            if(description != null)
                etTaskDescription.setText(description);
        }

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
                ImagePicker.with(EditTaskActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                btnTaskImageVideo.setText("Picture chosen");
            }
        });

        btnTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditTaskActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;

                if (month < 10) {

                    if (day < 10) {
                        String date = "0" + month + "/" + "0" + day + "/" + year;
                        btnTaskDate.setText(date);
                    }
                    else {
                        String date = "0" + month + "/" + day + "/" + year;
                        btnTaskDate.setText(date);
                    }

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

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditTaskActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mTimeSetListener, 12, 0, false);

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

                final String[] fonts = {"Never", "Every Day", "Every Week", "Every Month", "Every Year"};

                AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskActivity.this);
                builder.setTitle("Select how often to repeat the task");
                builder.setItems(fonts, new DialogInterface.OnClickListener() {@
                        Override
                public void onClick(DialogInterface dialog, int which) {
                    if ("Never".equals(fonts[which])) {
                        btnRepeat.setText("Never");
                        //Toast.makeText(AddTaskActivity.this, "you nailed it", Toast.LENGTH_SHORT).show();
                    } else if ("Every Day".equals(fonts[which])) {
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

                if (TextUtils.isEmpty(etTaskName.getText())) {
                    etTaskName.setError("Task Name is required!");
                } else if (TextUtils.isEmpty(btnTaskDate.getText())) {
                    btnTaskDate.setError("Task Date is required!");
                } else if (TextUtils.isEmpty(btnTaskStartTime.getText())) {
                    btnTaskStartTime.setError("Task Start Time is required!");
                }

                Intent myIntent = new Intent(getApplicationContext(), NotifyService.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getService(EditTaskActivity.this, 0, myIntent, 0);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.AM_PM, Calendar.AM);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);

                if (name != null) {
                    saveSchedule(etTaskName.getText().toString(), etTaskDescription.getText().toString(),
                            btnTaskDate.getText().toString(), btnTaskStartTime.getText().toString(), currentUser, photoFile, name);
                    deleteTask(etTaskName.getText().toString(), name);
                }
            }
        });
        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask(etTaskName.getText().toString(), name);
            }
        });
    }

    private void saveSchedule(String taskName, String taskDescription, String taskDate, String taskTime, ParseUser currentUser, File photoFile, String name) {
        Schedule schedule = new Schedule();

        try {
            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm aa");
            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
            schedule.setTaskTimeNumber(date24Format.format(date12Format.parse(taskTime)).replace(":", ""));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        schedule.setTaskName(taskName);
        schedule.setTaskDescription(taskDescription);
        schedule.setTaskDate(taskDate);
        schedule.setTaskTime(taskTime);
        schedule.setUser(currentUser);
        //      schedule.setImage(new ParseFile(photoFile));

        schedule.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "error", e);
                    Toast.makeText(EditTaskActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditTaskActivity.this, "Task updated!", Toast.LENGTH_SHORT).show();
                    etTaskName.setText("");
                    etTaskDescription.setText("");
                    btnTaskDate.setText("");
                    btnTaskStartTime.setText("");

                }
            }
        });
    }
    private void deleteTask(String taskName, String name) {
        // Configure Query with our query.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Schedule");

        // adding a condition where our course name
        // must be equal to the original course
        query.whereEqualTo("taskname", taskName);
        query.include(Schedule.KEY_USER);
        query.whereEqualTo(Schedule.KEY_USER, ParseUser.getCurrentUser());
        query.whereEqualTo(Schedule.KEY_TASK_COMPLETED, "no");

        // on below line we are finding the course with the course name.
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                // if the error is null.
                if (e == null) {
                    // on below line we are getting the first course and
                    // calling a delete method to delete this course.
                    objects.get(0).deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            // inside done method checking if the error is null or not.
                            if (e == null) {
                                // if the error is not null then we are displaying a toast message and opening our home activity.
                                if(name == null)
                                    Toast.makeText(getApplicationContext(), "Task Deleted..", Toast.LENGTH_SHORT).show();
                                Intent backIntent = new Intent(getApplicationContext(), SchedulerMenuActivity.class);
                                startActivity(backIntent);
                                finish();
                            } else {
                                // if we get error we are displaying it in below line.
                            }
                        }
                    });
                }
            }
        });
    }

}