package com.bsuir.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    List<String> data;
    Context context;

    public TasksAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    private void removeItem(int i) {
        data.remove(i);
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
        holder.checkBoxTask.setText(data.get(position));
        holder.checkBoxTask.setChecked(false);
        holder.checkBoxTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBoxTask.isChecked()) {
                    removeItem(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // Task item
        CheckBox checkBoxTask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            checkBoxTask.setChecked(false);
        }
    }
}
