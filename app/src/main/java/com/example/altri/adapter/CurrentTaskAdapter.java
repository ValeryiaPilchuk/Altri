package com.example.altri.adapter;

import static com.parse.Parse.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altri.R;
import com.example.altri.Schedule;
import com.example.altri.SchedulerMenuActivity;

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
        private Button btnChangeTime;
        private Button btnCompleted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            imTaskImage = itemView.findViewById(R.id.imTaskImage);
            tvTaskTime = itemView.findViewById(R.id.tvTaskTime);
            btnChangeTime = itemView.findViewById(R.id.btnChangeTime);
            btnCompleted = itemView.findViewById(R.id.btnCompleted);
            Intent inspiration = new Intent(getApplicationContext(), SchedulerMenuActivity.class);


            this.listener = listener;

            btnChangeTime.setOnClickListener(this);
            btnCompleted.setOnClickListener(this);

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
                }
            });

        }

        public void bind(Schedule task) {
            tvTaskName.setText(task.getTaskName());
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




