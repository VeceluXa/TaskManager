package com.example.myapplication;

import android.content.Context;
import java.util.ArrayList;

public class Task extends androidx.appcompat.widget.AppCompatTextView {

    protected static ArrayList<String> tasks  = new ArrayList<String>();

    public Task(Context context, String text){
        super(context);
        this.setText(text);
        tasks.add(text);
    }

    public static ArrayList<String> getTasks(){
        return tasks;
    }

}
