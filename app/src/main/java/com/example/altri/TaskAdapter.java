package com.example.altri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altri.Schedule;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context context;
    private List<Schedule> tasks;

    public TaskAdapter(Context context, List<Schedule> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.task,parent,false);
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

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView messageTV;
        private TextView timeTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.task_message);
            timeTV = itemView.findViewById(R.id.time_of_event);
        }

        public void bind(Schedule task) {
            messageTV.setText(task.getTaskName());
            timeTV.setText(task.getTaskTime());
        }
    }


}




