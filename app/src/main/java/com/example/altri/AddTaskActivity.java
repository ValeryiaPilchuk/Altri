package com.example.altri;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.altri.Notification.AlertReceiver;
import com.example.altri.Notification.NotificationHelper;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

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
    private File photoFile;
    public String photoFileName = "photo.jpg";
    private String objectID;
    private ImageView image;
    Uri selectedImage;
    String part_image;
    Bitmap bitmap;
    ParseFile parseFile;



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
        image = findViewById(R.id.taskImage);
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

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
                /*
                ImagePicker.with(AddTaskActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(); */
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

                final String[] fonts = {"Never", "Every Day", "Every Week", "Every Month", "Every Year"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddTaskActivity.this);
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

                saveSchedule(etTaskName.getText().toString(), etTaskDescription.getText().toString(),
                        btnTaskDate.getText().toString(), btnTaskStartTime.getText().toString(), currentUser, parseFile);
            }
        });
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();                                                         // Get the image file URI
            String[] imageProjection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, imageProjection, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
                int indexImage = cursor.getColumnIndex(imageProjection[0]);
                part_image = cursor.getString(indexImage);
                btnTaskImageVideo.setText(part_image);                                                        // Get the image file absolute path
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    image.setImageBitmap(bitmap);                                                       // Set the ImageView with the bitmap of the image
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    parseFile = new ParseFile("img.png", bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void saveSchedule(String taskName, String taskDescription, String taskDate,
                              String taskTime, ParseUser currentUser, ParseFile parseFile) {
        Schedule schedule = new Schedule();

        try {
            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm aa");
            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
            schedule.setTaskTimeNumber(date24Format.format(date12Format.parse(taskTime)).replace(":", ""));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (parseFile != null)
            schedule.setImage(parseFile);
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
                    Toast.makeText(AddTaskActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTaskActivity.this, "Task added!", Toast.LENGTH_SHORT).show();

                    etTaskName.setText("");
                    etTaskDescription.setText("");
                    btnTaskDate.setText("");
                    btnTaskStartTime.setText("");

                }
            }
        });
        startAlarm(taskTime, taskDate);
    }

    public void startAlarm(String tasktime, String taskdate) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String fulldate = tasktime + " " + taskdate;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a MM/dd/yyyy", Locale.ENGLISH);

        try {
            c.setTime(sdf.parse(fulldate));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        Log.d("alarm", "alarm added " + c.getTimeInMillis());

    }
}