package com.bsuir.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TaskDatabaseHelper database = new TaskDatabaseHelper(this);
    TasksAdapter tasksAdapter;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            data = new ArrayList<>(database.getAllTasksNames());
            System.out.println("PRINT DATA");
            System.out.println(data);
            System.out.println("Everything is ok");
            recyclerView = findViewById(R.id.recyclerViewTasks);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            tasksAdapter = new TasksAdapter(this, data);
            recyclerView.setAdapter(tasksAdapter);
        } catch (CursorIndexOutOfBoundsException e){
            System.out.println("Error is " + e);
        }
    }

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

    public void onCreateTask(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}