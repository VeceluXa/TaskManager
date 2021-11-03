package com.bsuir.taskmanager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Database {
    List<String> data = new LinkedList<String>();

    public List<String> getData() {
        return data;
    }

    public void setData(String data) {
        this.data.add(0, data);
    }
}
