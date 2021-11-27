package com.bsuir.taskmanager;

import com.bsuir.taskmanager.Adapters.TasksAdapter;

import junit.framework.TestCase;

import java.util.ArrayList;

public class TasksAdapterTest extends TestCase {

    public void testReturnCount() {
        ArrayList<String> data = new ArrayList<>();
        data.add("123");
        TasksAdapter tasksAdapter = new TasksAdapter(data);
        assertEquals(0, tasksAdapter.getItemCount());
    }
}