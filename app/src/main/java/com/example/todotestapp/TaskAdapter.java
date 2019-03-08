package com.example.todotestapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskList;

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView textTask;
        TextView textPriority;
        ImageView imageView;

        public TaskViewHolder(View itemView){
            super(itemView);
            this.textTask = (TextView)itemView.findViewById(R.id.task);
            this.textPriority = (TextView)itemView.findViewById(R.id.rb_priority);
            this.imageView = itemView.findViewById(R.id.sevearityView);
        }
    }

    public TaskAdapter(ArrayList<Task> data){
        this.taskList = data;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_items, parent, false);

        view.setOnClickListener(AllTaskList.myOnClickListener);

        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder taskViewHolder,final int position) {

        TextView textTask = taskViewHolder.textTask;
        TextView textPriority = taskViewHolder.textPriority;

        textTask.setText(taskList.get(position).getTaskName());
        textPriority.setText(taskList.get(position).getTaskPriority());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
