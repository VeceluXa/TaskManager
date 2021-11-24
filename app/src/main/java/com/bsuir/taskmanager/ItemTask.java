package com.bsuir.taskmanager;

import java.util.ArrayList;

public class ItemTask {
    private int id;
    private String taskName;
    private ArrayList<String> subtasks;

    ItemTask(int id, String taskName, ArrayList<String> subtasks){
        this.id = id;
        this.taskName = taskName;
        this.subtasks = subtasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public ArrayList<String> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<String> subtasks) {
        this.subtasks = subtasks;
    }
}
