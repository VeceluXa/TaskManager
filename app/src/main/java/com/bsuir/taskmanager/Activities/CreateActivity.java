package com.bsuir.taskmanager.Activities;

<<<<<<< HEAD:app/src/main/java/com/bsuir/taskmanager/CreateActivity.java
import android.content.Intent;
=======

>>>>>>> 5a5e9bf512000dd2e825d2cabf464d0bf788cab2:app/src/main/java/com/bsuir/taskmanager/Activities/CreateActivity.java
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bsuir.taskmanager.Adapters.SubtaskAdapter;
import com.bsuir.taskmanager.R;
import com.bsuir.taskmanager.TaskDatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class where tasks are created
 */
public class CreateActivity extends AppCompatActivity {

    /**
     * Text field for task name
     */
    private EditText task;

    /**
     * Adapter of subtasks for RecyclerView
     */
    private SubtaskAdapter adapter = null;
    /**
     * RecyclerView to show subtasks
     */
    private RecyclerView rv;
    /**
     * Subtasks
     */
    private ArrayList<String> subtasks;
    /**
     * Object of TaskDatabaseHelper to communicate with database
     */
    private final TaskDatabaseHelper taskDatabaseHelper = new TaskDatabaseHelper(this);

    /**
     * This method gets called when CreateActivity starts
     * @param savedInstanceState saved configuration
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create);

        task = findViewById(R.id.taskTODO);
        task.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            /**
             * This method gets called when user presses Enter to create new task
             * @param s editable string
             */
            @Override
            public void afterTextChanged(Editable s) {
                if (adapter == null) {
                   adapter = new SubtaskAdapter();
                   rv.setAdapter(adapter);
               }
            }
        });

//        task.setOnFocusChangeListener((view, hasFocus) -> {
//            if (!hasFocus) {
//                if (adapter == null) {
//                    adapter = new SubtaskAdapter();
//                    rv.setAdapter(adapter);
//                }
//            }
//        });
        rv = findViewById(R.id.subtasksList);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * This method gets called when user saves task
     * @param view view to call MainActivity
     */
    public void onSaveTask(View view) {
        String taskName = task.getText().toString();
        if(adapter != null)
            subtasks = adapter.getSubtasks();
        else
            subtasks.add(null); // Error line! //
        taskDatabaseHelper.insertTask(taskName, subtasks);
        //taskDatabaseHelper.updateTaskByName(taskName, subtasks);
        HashMap<Integer, String[]> data = taskDatabaseHelper.getAllTasks();
        for(int id : data.keySet())
            System.out.println(data.get(id)[0] + " ");

        // Close CreateActivity by calling onFinish()
        finish();
    }
}