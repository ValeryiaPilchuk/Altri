package com.example.altri.adapters;

import static com.parse.Parse.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.altri.AddTaskActivity;
import com.example.altri.EditTaskActivity;
import com.example.altri.MainActivity;
import com.example.altri.R;
import com.example.altri.Schedule;

import org.w3c.dom.Text;

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
    public int getItemCount() { return tasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView task_message;
        private TextView time_of_event;
        private TextView description;
        private TextView date;

        TaskAdapter.MyClickListener listener;
        Intent taskIntent = new Intent(getApplicationContext(), EditTaskActivity.class);


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_message = itemView.findViewById(R.id.task_message);
            time_of_event = itemView.findViewById(R.id.time_of_event);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);


            task_message.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    context.startActivity(taskIntent);
                }
            });
        }

        public void bind(Schedule task) {
            task_message.setText(task.getTaskName());
            time_of_event.setText(task.getTaskTime());
            description.setText(task.getTaskDescription());
            date.setText(task.getTaskDate());

            taskIntent.putExtra("name", task_message.getText());
            taskIntent.putExtra("time", time_of_event.getText());
            taskIntent.putExtra("description", description.getText());
            taskIntent.putExtra("date", date.getText());
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.task_message:
                    listener.onMessageClick(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }
    }


    public TaskAdapter.MyClickListener onClickListener;

    public interface MyClickListener {
        void onMessageClick(int p);

    }
}




