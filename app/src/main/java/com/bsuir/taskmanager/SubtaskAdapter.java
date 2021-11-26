package com.bsuir.taskmanager;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
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
        //subtasks.add(0, null);
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
    public void onBindViewHolder(@NonNull SubtaskViewHolder holder,int position) {
        int maxIndex = count - 1;

        if(maxIndex > 1) {
            if (position < maxIndex) {
                System.out.println("Putting on: " + position + " - " + maxIndex + "; " + subtasks.get(position));
                holder.subtaskField.setText(subtasks.get(position));
                holder.delBtn.setVisibility(View.VISIBLE);
            } else {
                holder.subtaskField.setText("");
                holder.delBtn.setVisibility(View.GONE);
            }
        }

        holder.subtaskField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                //int maxIndex = count - 1;
                //int position = holder.getAdapterPosition();
                //System.out.println(position + " - " + maxIndex);
                System.out.println(editable.toString());
                System.out.println(subtasks);
                try {
                    subtasks.set(holder.getAdapterPosition(), editable.toString());
                } catch (IndexOutOfBoundsException e){
                    //subtasks.add(holder.getAdapterPosition(), editable.toString());
                    if(count <= 10)
                        addItem(editable.toString());
                    holder.delBtn.setVisibility(View.VISIBLE);
                }
                //System.out.println(subtasks);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        });

        /*holder.subtaskField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View ___, int __, KeyEvent keyEvent) {
                String text = holder.subtaskField.getText().toString();
                int maxIndex = count - 1;
                int position = holder.getAdapterPosition();
                System.out.println(position + " - " + maxIndex);
                System.out.println(text);
                System.out.println(subtasks);

                if((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                        (position == maxIndex) &&
                        (!text.equals(""))){
                    addItem(text);
                    System.out.println("Setting visibilty");
                    holder.delBtn.setVisibility(View.VISIBLE);
                    System.out.println("Adder: " + subtasks);
                    return true;
                }
                else {
                    System.out.println("We goes there");
                    //System.out.println(subtasks);
                    return false;
                }
            }
        });*/

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
        System.out.println("Trying to add 2");
        subtasks.add(text);
        System.out.println("Notify");
        System.out.println(count);
        try {
            notifyItemInserted(count);
        } catch (Throwable e){
            System.out.println(e);
        }
        System.out.println("Counting");
        count++;
        System.out.println("Counted");
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



