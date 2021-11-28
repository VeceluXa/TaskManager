package com.bsuir.taskmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bsuir.taskmanager.Adapters.SubtaskAdapter;
import com.bsuir.taskmanager.R;
import com.bsuir.taskmanager.TaskDatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Window to edit tasks from main screen
 */
public class EditActivity extends AppCompatActivity {

    /**
     * Id of task
     */
    private int id;
    /**
     * Class to connect to SQLite database
     */
    private final TaskDatabaseHelper taskDatabaseHelper = new TaskDatabaseHelper(this);
    /**
     * Adapter for RecyclerView
     */
    private SubtaskAdapter adapter;
    /**
     * Array of subtasks from database
     */
    private ArrayList<String> subtasks;
    /**
     * Field with task name
     */
    private EditText task;

    /**
     * This method gets called when activity is created
     * @param savedInstanceState saved configuration
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Get task's id from intent
        id = getIntent().getExtras().getInt(Intent.EXTRA_INDEX);

        // Get data from database
        HashMap<String, String[]> data = taskDatabaseHelper.getTaskByIndex(id);
        String taskName = (String) data.keySet().toArray()[0];
        try {
            subtasks = new ArrayList<>(Arrays.asList(Objects.requireNonNull(data.get(taskName))));
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

        // Add RecyclerView and it's Adapter
        RecyclerView rv = findViewById(R.id.subtasksList);
        adapter = new SubtaskAdapter(subtasks);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Set task name text
        task = findViewById(R.id.taskTODO);
        task.setText(taskName);
    }

        /*task.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            /**
             * This method gets called when user presses Enter to create new task
             * @param s editable string

            @Override
            public void afterTextChanged(Editable s) {
                if (adapter == null) {
                    adapter = new SubtaskAdapter(subtasks);
                    rv.setAdapter(adapter);
                }
            }
        });*/
//        task.setOnFocusChangeListener((view, hasFocus) -> {
//            if (!hasFocus) {
//                if (adapter == null) {
//                    adapter = new SubtaskAdapter();
//                    rv.setAdapter(adapter);
//                }
//            }
//        });
//    }


    /**
     * This method gets called when user presses "Save" button
     * @param view view to get button listener
     */
    public void onSaveTask(View view) {
        String taskName = task.getText().toString();
        subtasks = adapter.getSubtasks();
        // Adapter is always != null cause it was constructed in onCreate
//        if(adapter != null)
//            subtasks = adapter.getSubtasks();
//        else
//            subtasks.add(null); // Error line! //
        taskDatabaseHelper.updateTaskByIndex(id, taskName, subtasks);
        //taskDatabaseHelper.updateTaskByName(taskName, subtasks);
        HashMap<Integer, String[]> data = taskDatabaseHelper.getAllTasks();
        for(int id : data.keySet())
            System.out.println(Objects.requireNonNull(data.get(id))[0] + " ");

        // Close CreateActivity by calling onFinish()
        finish();
    }

    /**
     * This method gets called when user presses "Delete" button
     * @param view view to get button listener
     */
    public void onDeleteTask(View view){
        // Delete task
        taskDatabaseHelper.deleteTaskByIndex(id);

        // Close CreateActivity by calling onFinish()
        finish();
    }

}