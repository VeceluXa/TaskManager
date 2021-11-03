package com.bsuir.taskmanager;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder> {

    private int count;
    private final ArrayList<String> subtasks = new ArrayList<String>();

    SubtaskAdapter(){

        count = 1;
    }

    @NonNull
    @Override
    public SubtaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.view_holder_example, parent, false);

        return new SubtaskViewHolder(view);
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull SubtaskViewHolder holder, int position) {
        int maxIndex = count - 1;

        if(maxIndex > 1) {
            if (position < maxIndex) {
                holder.subtaskField.setText(subtasks.get(position));
                holder.delBtn.setVisibility(View.VISIBLE);
            } else {
                holder.subtaskField.setText("");
                holder.delBtn.setVisibility(View.GONE);
            }
        }

        holder.subtaskField.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View ___, int __, KeyEvent keyEvent) {
                int position = holder.getAdapterPosition();
                String text = holder.subtaskField.getText().toString();
                int maxIndex = count - 1;

                if((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                        (position == maxIndex) &&
                        (!text.equals(""))){
                    addItem(text);
                    holder.delBtn.setVisibility(View.VISIBLE);
                    return true;
                }
                else
                    return false;
            }
        });

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rmItem(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return count;
    }

    void addItem(String text){
        subtasks.add(text);
        notifyItemInserted(count);
        count++;
    }

    void rmItem(int position){
        subtasks.remove(position);
        notifyItemRemoved(position);
        count--;
    }

    public ArrayList<String> getSubtasks(){
        return subtasks;
    }

    static class SubtaskViewHolder extends RecyclerView.ViewHolder{
        EditText subtaskField;
        ImageButton delBtn;
        SubtaskViewHolder(View view){
            super(view);
            subtaskField = (EditText) view.findViewById(R.id.subtask);
            delBtn = (ImageButton) view.findViewById(R.id.delbtn);
            delBtn.setVisibility(View.GONE);
        }
    }
}
