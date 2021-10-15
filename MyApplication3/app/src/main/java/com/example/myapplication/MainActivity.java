package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.GridLayout;

public class MainActivity extends Activity {

    public static String TASK_NAME = "taskname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String text = intent.getStringExtra(TASK_NAME);
        if(text!=null) {
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout);
            ListView listView = new ListView(this);
            listView.setLayoutParams(new ViewGroup.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            Task task = new Task(this, text);
            ArrayAdapter<String> taskList = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    Task.getTasks());
            listView.setAdapter(taskList);
            rl.addView(listView);
        }
    }

    public void goToCreationActivity(View view){
        Intent creationActivity = new Intent(this, CreationActivity.class);
        startActivity(creationActivity);
    }
}