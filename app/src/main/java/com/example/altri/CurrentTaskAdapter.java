package com.example.altri;

import static com.parse.Parse.getApplicationContext;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altri.Schedule;
import com.parse.ParseFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentTaskAdapter extends RecyclerView.Adapter<CurrentTaskAdapter.ViewHolder> {

    private Context context;
    private List<Schedule> tasks;

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

        private TextView tvTaskName;
        private ImageView imTaskImage;
        private TextView tvTaskTime;
        private TextView tvTaskDescription;

        private ImageButton btnBack;
        private Button btnChangeTime;
        private Button btnCompleted;

        private AlertDialog.Builder dialogBuilder;
        private AlertDialog dialog;

        private TimePickerDialog.OnTimeSetListener mTimeSetListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
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

            btnCompleted.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Log.d("msg", "Completed button clicked");


                }

            });

            btnChangeTime.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Log.d("msg", "ChangeTime button clicked");
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(), android.R.style.Theme_Holo_Dialog_MinWidth, mTimeSetListener, 12, 0, false);

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
                        btnChangeTime.setText(display12hours.format(date));
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }


                }
            };


        }

        public void bind(Schedule task) {
            tvTaskName.setText(task.getTaskName());
            tvTaskDescription.setText(task.getTaskDescription());
            //  imTaskImage.setImage(task.getTaskImage());
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

    public MyClickListener onClickListener;

    public interface MyClickListener {
        void onChangeTime(int p);

        void onCompleted(int p);

    }
}




