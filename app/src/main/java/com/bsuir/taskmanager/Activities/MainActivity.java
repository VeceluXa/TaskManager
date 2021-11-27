package com.bsuir.taskmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bsuir.taskmanager.Adapters.TasksAdapter;
import com.bsuir.taskmanager.R;
import com.bsuir.taskmanager.TaskDatabaseHelper;

import java.util.ArrayList;

/**
 * Class for main window where all tasks are shown
 */
public class MainActivity extends AppCompatActivity {

    /**
     * RecyclerView to display tasks on MainActivity
     */
    RecyclerView recyclerView;
    /**
     * Database where tasks are stored
     */
    TaskDatabaseHelper database = new TaskDatabaseHelper(this);
    /**
     * Adapter for RecyclerView
     */
    TasksAdapter tasksAdapter;
    /**
     * ArrayList of Database tasks for ease of use
     */
    ArrayList<String> data;

    /**
     * This method gets called when app starts
     * @param savedInstanceState saved configuration
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set data from database
        data = new ArrayList<>(database.getAllTasksNames());

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TasksAdapter(this, data);
        // Add listener to adapter
        recyclerView.setAdapter(tasksAdapter);

    }

    /**
     * This method gets called when activity goes in focus.
     * In our case after CreateActivity is finished
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        // Update Tasks
        data.clear();
        data.addAll(database.getAllTasksNames());

        // Replace tasksAdapter with the new one
        tasksAdapter = new TasksAdapter(this, data);
        // Swap adapter in RecyclerView
        recyclerView.setAdapter(tasksAdapter);

    }

    /**
     * Button listener to create new task
     * @param view view to pass to new activity
     */
    public void onCreateTask(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}