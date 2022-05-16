package com.example.altri.adapter;

import static com.parse.Parse.getApplicationContext;
import com.bumptech.glide.Glide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altri.MainActivity;
import com.example.altri.R;
import com.example.altri.Schedule;
import com.example.altri.SchedulerMenuActivity;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CurrentTaskAdapter extends RecyclerView.Adapter<CurrentTaskAdapter.ViewHolder> {

    private Context context;
    private List<Schedule> tasks;
    private String taskName;
    private String timeNumber;
    private String time;
    private String objectID;
    private DateTimeFormatter hourFormat;
    public CurrentTaskAdapter(Context context, List<Schedule> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.current_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule task = tasks.get(position);
        holder.bind(task);

    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MyClickListener listener;

        private TextView tvCurrentTaskName;
        private ImageView imTaskImage;
        private TextView tvTaskTime;
        private TextView tvTaskDescription;

        private ImageButton btnBack;
        private Button btnChangeTime;
        private Button btnCompleted;

        private AlertDialog.Builder dialogBuilder;
        private AlertDialog dialog;

        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrentTaskName = itemView.findViewById(R.id.tvCurrentTaskName);
            imTaskImage = itemView.findViewById(R.id.imTaskImage);
            tvTaskTime = itemView.findViewById(R.id.tvTaskTime);
            tvTaskDescription = itemView.findViewById(R.id.tvTaskDescription);
            btnBack = itemView.findViewById(R.id.btnBack);
            btnChangeTime = itemView.findViewById(R.id.btnChangeTime);
            btnCompleted = itemView.findViewById(R.id.btnCompleted);


            this.listener = listener;

            //btnBack.setOnClickListener(this);
            btnChangeTime.setOnClickListener(this);
            btnCompleted.setOnClickListener(this);

            Intent backIntent = new Intent(getApplicationContext(), SchedulerMenuActivity.class);

            Intent completedIntent = new Intent(getApplicationContext(), MainActivity.class);
            completedIntent.putExtra("Task", "completed");


            btnCompleted.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d("msg", "Completed button clicked");
                    deleteTask(taskName);
                    Log.d("taskname", taskName);
                    context.startActivity(completedIntent);
                }
            });

            btnChangeTime.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Log.d("msg", "ChangeTime button clicked");
                    Log.d("msg", String.valueOf(timeNumber));
                    Integer newTimeNumber =  Integer.parseInt(timeNumber) + 100;
                    String newTime = time;
                    try {
                        Date dateTime = dateFormat.parse(newTime);
                        dateTime.setTime(dateTime.getTime() + TimeUnit.HOURS.toMillis(1));
                        newTime = dateFormat.format(dateTime);
                        updateData(taskName, timeNumber, newTimeNumber, newTime);
                        Log.d("msg", newTime);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        public void bind(Schedule task) {
            tvCurrentTaskName.setText(task.getTaskName());
            taskName = tvCurrentTaskName.getText().toString();
            timeNumber = task.getTaskTimeNumber();
            time = task.getTaskTime();
            Log.d("taskname", task.getTaskName());
            tvTaskDescription.setText(task.getTaskDescription());
            ParseFile image = task.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(imTaskImage);
            }
            tvTaskTime.setText(task.getTaskTime());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnChangeTime:
                    listener.onChangeTime(this.getLayoutPosition());
                    break;
                case R.id.btnCompleted:
                    listener.onCompleted(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }
    }

    private void deleteTask(String taskName) {
        // Configure Query with our query.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Schedule");

        // adding a condition where our course name
        // must be equal to the original course name
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
                                Toast.makeText(getApplicationContext(), "Task Deleted..", Toast.LENGTH_SHORT).show();
                            } else {
                                // if we get error we are displaying it in below line.
                            }
                        }
                    });
                }
            }
        });
    }


    private void updateData(String taskName, String timeNumber, Integer newTimeNumber, String newTime) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Schedule");

        // adding a condition where our course name
        // must be equal to the original course name
        query.whereEqualTo("taskname", taskName);
        query.include(Schedule.KEY_USER);
        query.whereEqualTo(Schedule.KEY_USER, ParseUser.getCurrentUser());
        query.whereEqualTo(Schedule.KEY_TASK_COMPLETED, "no");

        // in below method we are getting the unique id
        // of the course which we have to make update.

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                // inside done method we check
                // if the error is null or not.
                if (e == null) {

                    // if the error is null then we are getting
                    // our object id in below line.
                    objectID = object.getObjectId().toString();
                    // after getting our object id we will
                    // move towards updating our course.
                    // calling below method to update our course.
                    query.getInBackground(objectID, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            // in this method we are getting the
                            // object which we have to update.
                            if (e == null) {

                                // in below line we are adding new data
                                // to the object which we get from its id.
                                // on below line we are adding our data
                                // with their key value in our object.
                                object.put("timeNumber", newTimeNumber.toString());
                                object.put("time", newTime);

                                // after adding new data then we are
                                // calling a method to save this data
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        // inside on done method we are checking
                                        // if the error is null or not.
                                        if (e == null) {
                                            // if the error is null our data has been updated.
                                            // we are displaying a toast message and redirecting
                                            // our user to home activity where we are displaying course list.
                                            Toast.makeText(getApplicationContext(), "Time Updated..", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                            i.putExtra("Task", "current");
                                            context.startActivity(i);
                                        } else {
                                            // below line is for error handling.
                                            Toast.makeText(getApplicationContext(), "Fail to update time " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                // on below line we are displaying a message
                                // if we don't get the object from its id.
                                Toast.makeText(getApplicationContext(), "Fail to update time " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // this is error handling if we don't get the id for our object
                    Log.d("fail","Fail to get object ID..");
                }
            }
        });
    }


    public MyClickListener onClickListener;

    public interface MyClickListener {
        void onChangeTime(int p);

        void onCompleted(int p);

    }
}