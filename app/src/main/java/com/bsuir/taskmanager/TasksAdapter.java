package com.bsuir.taskmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    //HashMap<String, String[]> data;
    TaskDatabaseHelper database;
    Context context;

    public TasksAdapter(Context context, TaskDatabaseHelper database) {
        this.context = context;
        this.database = database;
    }

    private void removeItem(int i) {
        database.deleteTaskByIndex(i);
        notifyItemRemoved(i);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO Fix data input to tasks viewholder in MainActivity
        HashMap<String, String[]> data = database.getTaskByIndex(position);
        holder.checkBoxTask.setText(data.keySet().toString());
        holder.checkBoxTask.setChecked(false);
        holder.checkBoxTask.setOnClickListener(view -> {
            if (holder.checkBoxTask.isChecked()) {
                removeItem(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Task item
        CheckBox checkBoxTask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            checkBoxTask.setChecked(false);
        }
    }
}
