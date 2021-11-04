package com.bsuir.taskmanager;

import java.util.LinkedList;
import java.util.List;

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
