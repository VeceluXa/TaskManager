package com.bsuir.taskmanager;

import java.util.LinkedList;
import java.util.List;


/**
 * This is test class for Unit Tests and JavaDoc
 */
public class Database {
    List<String> data = new LinkedList<>();

    public List<String> getData() {
        return data;
    }

    public void setData(String data) {
        this.data.add(0, data);
    }

    public int addition(int a, int b) {
        return a + b;
    }
}
