package com.bsuir.taskmanager;

import android.content.Context;
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
    LayoutInflater inflater;
    TaskDatabaseHelper database;
    ArrayList<String> data;
    private int count;
    private Context context;

    // Listener for TextView in ViewHolders
    private ItemClickListener mEditTaskListener;


    /**
     * Constructor for TasksAdapter
     * @param context context of MainActivity
     * @param data ArrayList for database
     */
    public TasksAdapter(Context context, ArrayList<String> data) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        database = new TaskDatabaseHelper(context);
        this.data = data;
        this.count = data.size();
    }

    public void addEditTaskListener(ItemClickListener listener) {
        mEditTaskListener = listener;
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

        // Clicked on checkbox button
        holder.checkBoxTask.setOnClickListener(view -> {
            if (holder.checkBoxTask.isChecked()) {
                removeItem(holder.getAdapterPosition());
            }
        });

        // Clicked on text
        holder.textTask.setOnClickListener(v -> {
            // Check if clicked
            if (mEditTaskListener != null) {
                // Call onEditTask method in MainActivity
                mEditTaskListener.onEditTask(position);
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

    // Interface for TextView listener
    public interface ItemClickListener {
        void onEditTask(int position);
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
