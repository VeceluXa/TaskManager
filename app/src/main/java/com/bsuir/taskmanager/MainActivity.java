package com.bsuir.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * Class for main window where all tasks are shown
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.ItemClickListener{

    RecyclerView recyclerView;
    TaskDatabaseHelper database = new TaskDatabaseHelper(this);
    TasksAdapter tasksAdapter;
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
        tasksAdapter.addEditTaskListener(this);
        recyclerView.setAdapter(tasksAdapter);

    }

    /**
     * This method gets called when activity becomes in focus
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        // Update Tasks
        data.clear();
        System.out.println(database.getAllTasks().keySet());
        data.addAll(database.getAllTasksNames());
        System.out.println(data);
        tasksAdapter.notifyDataSetChanged();
        System.out.println(database.getAllTasks());
    }

    /**
     * Button listener to create new task
     * @param view view to pass to new activity
     */
    public void onCreateTask(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    // Implement listener's method
    @Override
    public void onEditTask(int position) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(Intent.EXTRA_INDEX, position);
        startActivity(intent);
    }
}