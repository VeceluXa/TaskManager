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

/**
 * Adapter for RecyclerView in MainActivity
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    LayoutInflater inflater;
    TaskDatabaseHelper database;
    ArrayList<String> data;
    private int count;

    /**
     * Constructor for TasksAdapter
     * @param context context of MainActivity
     * @param data ArrayList for database
     */
    public TasksAdapter(Context context, ArrayList<String> data) {
        this.inflater = LayoutInflater.from(context);
        database = new TaskDatabaseHelper(context);
        this.data = data;
        this.count = data.size();
    }

     /**
     * Remove item from database and RecyclerView by index.
     * @param i position of ViewHolder
     */
    private void removeItem(int i) {
        // TODO Fix delete task by index
        database.deleteTaskByIndex(i+1);
        data.remove(i);
        notifyItemRemoved(i);
        count--;

        // Note: logging
        System.out.println(i);
        System.out.println(database.getAllTasks().keySet());
    }

    /**
     * This method gets called when ViewHolder is created
     * @param parent MainActivity
     * @param viewType Type of view
     * @return ViewHolder constructor
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Gets called when ViewHolder is binded to RecyclerView
     * @param holder holder for each item
     * @param position position of viewholder
     */
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

    /**
     * Returns count of recyclerview items
     * @return size of data list
     */
    @Override
    public int getItemCount() {
        return count;
    }

    /**
     * Class for each item in recyclerview
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Task item
        CheckBox checkBoxTask;
        TextView textTask;

        /**
         * Constructor for ViewHolder
         * @param itemView view for each item
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            textTask = itemView.findViewById(R.id.textTask);
            //checkBoxTask.setChecked(false);
        }
    }
}
