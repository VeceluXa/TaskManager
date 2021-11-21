package com.bsuir.taskmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    LayoutInflater inflater;
    TaskDatabaseHelper database;
    ArrayList<String> data;
    private int count;

    public TasksAdapter(Context context, ArrayList<String> data) {
        this.inflater = LayoutInflater.from(context);
        database = new TaskDatabaseHelper(context);
        this.data = data;
        this.count = data.size();
    }

    private void removeItem(int i) {
        // TODO Fix delete task by index
        System.out.println("DELETING");
        database.deleteTaskByIndex(i);
        System.out.println("FUCK");
        data.remove(i);
        notifyItemRemoved(i);
        count--;

        // Note: logging
        System.out.println(i);
        System.out.println(database.getAllTasks().keySet());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO Fix data to constructor
        holder.checkBoxTask.setText(data.get(position));
        holder.checkBoxTask.setChecked(false);
        holder.checkBoxTask.setOnClickListener(view -> {
            if (holder.checkBoxTask.isChecked()) {
                removeItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Task item
        CheckBox checkBoxTask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            //checkBoxTask.setChecked(false);
        }
    }
}
