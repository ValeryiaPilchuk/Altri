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

        View view = LayoutInflater.from(context).inflate(R.layout.current_task,parent,false);
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

        private TextView tvTaskName;
        private ImageView imTaskImage;
        private TextView tvTaskTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            imTaskImage = itemView.findViewById(R.id.imTaskImage);
            tvTaskTime = itemView.findViewById(R.id.tvTaskTime);
        }

        public void bind(Schedule task) {
            //messageTV.setText(task.getTaskName());
            //timeTV.setText(task.getTaskTime());
            tvTaskName.setText(task.getTaskName());
            //  imTaskImage.setText(task.getTaskImage());
            tvTaskTime.setText(task.getTaskTime());
        }
    }


}




