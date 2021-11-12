package com.bsuir.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TaskDatabaseHelper database = new TaskDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //List<String> data = database.getData();
       // HashMap<String, String[]> data = database2.getAllTasks();

        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TasksAdapter tasksAdapter = new TasksAdapter(getApplicationContext(), database);
        recyclerView.setAdapter(tasksAdapter);

        // TEST FOR SONARCLOUD'S UNIT TESTS
        //database.addition(3,4);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update Tasks
        //recyclerView.notifyAll();
        System.out.println(database.getAllTasks());
    }

    public void onCreateTask(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}