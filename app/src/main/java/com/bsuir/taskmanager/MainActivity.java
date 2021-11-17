package com.bsuir.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

        data = new ArrayList<>(database.getAllTasks().keySet());

        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TasksAdapter(this, data);
        recyclerView.setAdapter(tasksAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update Tasks
        data.clear();
        data.addAll(database.getAllTasks().keySet());
        System.out.println(data);
        tasksAdapter.notifyDataSetChanged();
        System.out.println(database.getAllTasks());
    }

    public void onCreateTask(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}