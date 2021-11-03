package com.bsuir.taskmanager;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CreateActivity extends AppCompatActivity {

    private EditText task;

    private SubtaskAdapter adapter = null;
    private RecyclerView rv;

    private ArrayList<String> subtasks;
    private String taskName;

    private final TaskDatabaseHelper taskDatabaseHelper = new TaskDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create);

        task = (EditText) findViewById(R.id.taskTODO);
        task.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (adapter == null) {
                        adapter = new SubtaskAdapter();
                        rv.setAdapter(adapter);
                    }
                }
            }
        });
        rv = (RecyclerView) findViewById(R.id.subtasksList);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


    public void saveTask(View view) {
        taskName = task.getText().toString();
        if(adapter != null)
            subtasks = adapter.getSubtasks();
        else
            subtasks.add("");
        taskDatabaseHelper.insertTask(taskName, subtasks);
        Intent MainActivity = new Intent(this, MainActivity.class);
        startActivity(MainActivity);
    }
}