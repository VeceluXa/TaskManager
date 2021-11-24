package com.bsuir.taskmanager;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


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
        task.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

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


    // TODO Fix bug onSaveTask.
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
        finish();
    }
}