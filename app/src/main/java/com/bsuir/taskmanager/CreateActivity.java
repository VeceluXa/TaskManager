package com.bsuir.taskmanager;


import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CreateActivity extends AppCompatActivity {

    private EditText task;

    private SubtaskAdapter adapter = null;
    private RecyclerView rv;

    private ArrayList<String> subtasks;

    private final TaskDatabaseHelper taskDatabaseHelper = new TaskDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create);

        task = findViewById(R.id.taskTODO);
        task.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (adapter == null) {
                    adapter = new SubtaskAdapter();
                    rv.setAdapter(adapter);
                }
            }
        });
        rv = findViewById(R.id.subtasksList);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


    // TODO Fix bug onSaveTask.
    public void onSaveTask(View view) {
        String taskName = task.getText().toString();
        if(adapter != null)
            subtasks = adapter.getSubtasks();
        else
            subtasks.add(""); // Error line! //
        taskDatabaseHelper.insertTask(taskName, subtasks);
        finish();
    }
}