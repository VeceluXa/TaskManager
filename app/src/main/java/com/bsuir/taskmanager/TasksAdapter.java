package com.bsuir.taskmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

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
        this.data = data;
        this.count = data.size();
    }

    /**
     * Remove item from Database and RecyclerView by index.
     * @param i
     */
    private void removeItem(int i) {
        // TODO Fix delete task by index
        database.deleteTaskByIndex(i);
        data.remove(i);
        notifyItemRemoved(i);
        count--;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        database = new TaskDatabaseHelper(holder.checkBoxTask.getContext());
        // TODO Fix data to constructor
        holder.textTask.setText(data.get(position));
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
        TextView textTask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            textTask = itemView.findViewById(R.id.textTask);
            //checkBoxTask.setChecked(false);
        }
    }
}
