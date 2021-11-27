package com.bsuir.taskmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bsuir.taskmanager.Adapters.SubtaskAdapter;
import com.bsuir.taskmanager.R;
import com.bsuir.taskmanager.TaskDatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    private EditText task;
    private int count=1;
    private SubtaskAdapter adapter = null;
    private RecyclerView rv;
    private int id;
    private ArrayList<String> subtasks;

    private final TaskDatabaseHelper taskDatabaseHelper = new TaskDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);
        id = getIntent().getExtras().getInt(Intent.EXTRA_INDEX);
        task = findViewById(R.id.taskTODO);
        HashMap<Integer, String[]> data = taskDatabaseHelper.getAllTasks();
        for(int id : data.keySet())
            System.out.println(data.get(id)[0] + " ");
        task.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            /**
             * This method gets called when user presses Enter to create new task
             * @param s editable string
             */
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


    /**
     * This method gets called when user saves task
     * @param view view to call MainActivity
     */
    public void onSaveTask(View view) {
        String taskName = task.getText().toString();
        if(adapter != null)
            subtasks = adapter.getSubtasks();
        else
            subtasks.add(null); // Error line! //
        taskDatabaseHelper.updateTaskByIndex(id+1, taskName, subtasks);
        //taskDatabaseHelper.updateTaskByName(taskName, subtasks);
        HashMap<Integer, String[]> data = taskDatabaseHelper.getAllTasks();
        for(int id : data.keySet())
            System.out.println(data.get(id)[0] + " ");

        // Close CreateActivity by calling onFinish()
        finish();
    }
    public void onDeleteTask(View view){
        taskDatabaseHelper.deleteTaskByIndex(id+1);
        finish();
    }

}