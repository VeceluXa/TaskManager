package com.bsuir.taskmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bsuir.taskmanager.R;

public class EditActivity extends AppCompatActivity {

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        id = getIntent().getExtras().getInt(Intent.EXTRA_INDEX);

        TextView textView = findViewById(R.id.editView);
        textView.setText(String.valueOf(id));
    }

    //TODO Add methods for EditActivity
}