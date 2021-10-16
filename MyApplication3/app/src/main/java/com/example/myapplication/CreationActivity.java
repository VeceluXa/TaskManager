package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CreationActivity extends Activity{

    private EditText task;
    private SubtaskAdapter adapter = null;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_creation);

        task = (EditText) findViewById(R.id.taskTODO);
        task.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(adapter == null){
                        adapter = new SubtaskAdapter();
                        rv.setAdapter(adapter);
                    }
                }
            }
        });
        rv = (RecyclerView) findViewById(R.id.subtasksList);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }



    public void saveTask(View view){
         String text = task.getText().toString();
        //Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        Intent goToMain = new Intent(this, MainActivity.class);
        goToMain.putExtra(MainActivity.TASK_NAME, text);
        startActivity(goToMain);
        if(task.isFocused()){
            Toast.makeText(this, "lol", Toast.LENGTH_LONG).show();
        }
    }
}