package com.bsuir.taskmanager.Adapters;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bsuir.taskmanager.R;

import java.util.ArrayList;

/**
 * Adapter for RecyclerView in CreateActivity
 */
public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder> {

    /**
     * Count for size of subtasks ArrayList
     */
    private int count;
    /**
     * Array list that stores subtasks
     */
    private ArrayList<String> subtasks = new ArrayList<>();

    /**
     * Basic constructor for subtasks
     */
    public SubtaskAdapter(ArrayList<String> subtasks){
        this.subtasks = subtasks;
        count = subtasks.size()+1;
    }

    public SubtaskAdapter(){
        this.count = 1;
    }

    /**
     * This method gets called when ViewHolder is created
     * @param parent parent of RecyclerView (CreateActivity)
     * @param viewType type of view
     * @return new ViewHolder
     */
    @NonNull
    @Override
    public SubtaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.subtask_item, parent, false);

        return new SubtaskViewHolder(view);
    }


    /**
     * This method gets called when ViewHolder is binded to screen
     * @param holder individual ViewHolder
     * @param position position of holder
     */
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
            /**
             * This method gets celled when user changes text in EditText
             * @param editable text that was edited
             */
            @Override
            public void afterTextChanged(Editable editable) {
                //int maxIndex = count - 1;
                //int position = holder.getAdapterPosition();
                try {
                    subtasks.set(holder.getAdapterPosition(), editable.toString());
                } catch (IndexOutOfBoundsException e){
                    System.out.println(e.getMessage());
                    //subtasks.add(holder.getAdapterPosition(), editable.toString());
                    if(count <= 10) {
                        System.out.println("Adding subtask");
                        addItem(editable.toString());
                    }
                    holder.delBtn.setVisibility(View.VISIBLE);
                }
            }

            /**
             * Unused method
             * @param charSequence unused parameter
             * @param i unused parameter
             * @param i1 unused parameter
             * @param i2 unused parameter
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            /**
             * Unused method
             * @param charSequence unused parameter
             * @param i unused parameter
             * @param i1 unused parameter
             * @param i2 unused parameter
             */
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

        holder.delBtn.setOnClickListener(view ->
                rmItem(holder.getAdapterPosition()));

    }

    @Override
    public int getItemCount() {
        return count;
    }

    /**
     * Add new subtask
     * @param text name of subtask
     */
    void addItem(String text){
        subtasks.add(text);
        try {
            notifyItemInserted(count);
        } catch (Throwable e){
            System.out.println(e.getMessage());
        }
        count++;
    }

    /**
     * Remove subtask
     * @param position position of subtask
     */
    void rmItem(int position){
        subtasks.remove(position);
        notifyItemRemoved(position);
        count--;
    }

    /**
     * Getter for subtasks
     * @return ArrayList of subtasks
     */
    public ArrayList<String> getSubtasks(){
        return subtasks;
    }

    /**
     * Class for individual ViewHolder
     */
    static class SubtaskViewHolder extends RecyclerView.ViewHolder{
        /**
         * Text field to enter subtask in
         */
        EditText subtaskField;
        /**
         * Button to delete subtask
         */
        ImageButton delBtn;

        /**
         * Constructor for SubtaskViewHolder
         * @param view view to get context
         */
        SubtaskViewHolder(View view){
            super(view);
            subtaskField = view.findViewById(R.id.subtask);
            delBtn = view.findViewById(R.id.delbtn);
            delBtn.setVisibility(View.GONE);
        }

    }
}



