package com.bsuir.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskDatabaseHelper {
    private Context context;
    TaskDatabaseAdapter taskDbAdapter;
    ContentValues taskValues;
    String taskNameClmn, subtasksNameClmn, tableName, indexNameClmn;

    TaskDatabaseHelper(Context context){
        this.context = context;
        taskDbAdapter = new  TaskDatabaseAdapter(this.context);
        // System.out.println("HELLO");
        taskNameClmn = taskDbAdapter.getTaskNameClmn();
        subtasksNameClmn = taskDbAdapter.getSubtasksNameClmn();
        indexNameClmn = taskDbAdapter.getIndexNameClmn();
        tableName = taskDbAdapter.getTableName();
    }


    /*public void insertTask(String taskName, String[] subtasks){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        taskValues = new ContentValues();
        taskValues.put(taskNameClmn, taskName);
        taskValues.put(subtasksNameClmn, convertArrToStr(subtasks));
        db.insert(tableName, null, taskValues);
        System.out.println("WELL DONE");
        db.close();
    }*/


    public void insertTask(String taskName, ArrayList<String> subtasks){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        taskValues = new ContentValues();
        taskValues.put(taskNameClmn, taskName);
        taskValues.put(subtasksNameClmn, convertArrToStr(subtasks));
        db.insert(tableName, null, taskValues);
        //db.execSQL("DROP TABLE " + tableName);
        System.out.println("WELL DONE");
        db.close();
    }

    public void updateTaskByName(String taskName, ArrayList<String> subtasks){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        taskValues = new ContentValues();
        taskValues.put(taskNameClmn, taskName);
        taskValues.put(subtasksNameClmn, convertArrToStr(subtasks));
        db.update(tableName, taskValues, taskNameClmn + " = ?", new String[] {taskName});
        //db.execSQL("DROP TABLE " + tableName);
        System.out.println("WELL DONE");
        db.close();
    }

    public void updateTaskByIndex(int index, String taskName, ArrayList<String> subtasks){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        taskValues = new ContentValues();
        taskValues.put(taskNameClmn, taskName);
        taskValues.put(subtasksNameClmn, convertArrToStr(subtasks));
        db.update(tableName, taskValues, indexNameClmn + " = ?", new String[] {Integer.toString(index)});
        //db.execSQL("DROP TABLE " + tableName);
        System.out.println("WELL DONE");
        db.close();
    }

    public void deleteTaskByName(String taskName){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        db.delete(tableName,
                taskNameClmn + " = ?",
                new String[] {taskName});
    }

    public void deleteTaskByIndex(int index){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        db.delete(tableName,
                indexNameClmn + " = ?",
                new String[] {Integer.toString(index)});
    }


    public HashMap<String,String[]> getTaskByName(String taskName){
        HashMap<String,String[]> data = new HashMap<>();
        SQLiteDatabase db = taskDbAdapter.getReadableDatabase();
        Cursor cursor = db.query(tableName,
                new String[] {taskNameClmn, subtasksNameClmn},
                taskNameClmn + " = ?",
                new String[] {taskName},
                null, null, null, "1");

        data.put(cursor.getString(0), cursor.getString(1).split(","));
        cursor.close();
        db.close();
        return data;
    }

    // TODO Get Task !String! by index
    public HashMap<String,String[]> getTaskByIndex(int index){
        HashMap<String,String[]> data = new HashMap<>();
        SQLiteDatabase db = taskDbAdapter.getReadableDatabase();
        Cursor cursor = db.query(tableName,
                new String[] {taskNameClmn, subtasksNameClmn},
                indexNameClmn + " = ?",
                new String[] {Integer.toString(index)},
                null, null, null, null);

        data.put(cursor.getString(0), cursor.getString(1).split(","));
        cursor.close();
        db.close();
        return data;
    }

    public HashMap<String,String[]> getAllTasks(){
        HashMap<String,String[]> data = new HashMap<>();
        SQLiteDatabase db = taskDbAdapter.getReadableDatabase();
        try {
            Cursor cursor = db.query(tableName,
                    new String[]{taskNameClmn, subtasksNameClmn},
                    null, null, null, null, null);
            while (true) {
                if (cursor.moveToNext()) {
                    data.put(cursor.getString(0), cursor.getString(1).split(","));
                } else
                    break;
            }
        } catch (SQLiteException e){
            System.out.println("errror");
        }
        return data;
    }

    /*private String convertArrToStr(Object[] arr){
        String str = "";
        for(int i = 0; i < arr.length; i++){
            str += arr.toString();
        }
        return str;
    }*/

    private String convertArrToStr(ArrayList<String> arr){
        String str = "";
        for(int i = 0; i < arr.size(); i++){
            str += arr.toString();
        }
        return str;
    }
}

