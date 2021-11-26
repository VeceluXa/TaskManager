package com.bsuir.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter for RecyclerView in MainActivity
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    /**
     * Database object
     */
    TaskDatabaseHelper database;
    /**
     * ArrayList that stores tasksName from database
     */
    ArrayList<String> data;
    /**
     * Counter for size of data
     */
    private int count;

    /**
     * Setter for data
     * @param data ArrayList of values from database
     */
    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    private final Context context;


    /**
     * Constructor for TasksAdapter
     * @param context context of MainActivity
     * @param data ArrayList for database
     */
    public TasksAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        database = new TaskDatabaseHelper(context);
        this.data = data;
        this.count = data.size();
    }


     /**
     * Remove item from database and RecyclerView by index.
     * @param i position of ViewHolder
     */
    private void removeItem(int i) {
        database.deleteTaskByIndex(i+1);
        data.remove(i);
        notifyItemRemoved(i);
        count--;
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
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
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

        // Clicked on CheckBox
        holder.checkBoxTask.setOnClickListener(v -> {
            if (holder.checkBoxTask.isChecked()) {
                removeItem(holder.getAdapterPosition());
            }
        });

        // Clicked on TextView
        holder.textTask.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra(Intent.EXTRA_INDEX, holder.getAdapterPosition());
            context.startActivity(intent);
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
        }
    }
}
