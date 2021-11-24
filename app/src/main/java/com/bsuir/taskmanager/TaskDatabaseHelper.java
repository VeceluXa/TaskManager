package com.bsuir.taskmanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;



public class TaskDatabaseHelper {
    private Context context;
    TaskDatabaseAdapter taskDbAdapter;
    ContentValues taskValues;
    String taskNameClmn, subtasksNameClmn, tableName, indexNameClmn;

    TaskDatabaseHelper(Context context){
        this.context = context;
        taskDbAdapter = new TaskDatabaseAdapter(this.context);
        // System.out.println("HELLO");
        taskNameClmn = taskDbAdapter.getTaskNameClmn();
        subtasksNameClmn = taskDbAdapter.getSubtasksNameClmn();
        indexNameClmn = taskDbAdapter.getIndexNameClmn();
        tableName = taskDbAdapter.getTableName();
    }


    public void insertTask(String taskName, String[] subtasks){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        taskValues = new ContentValues();
        taskValues.put(taskNameClmn, taskName);
        taskValues.put(subtasksNameClmn, convertArrToStr(subtasks));
        db.insert(tableName, null, taskValues);
        System.out.println("WELL DONE");
        db.close();
    }


    public void insertTask(String taskName, ArrayList<String> subtasks){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        taskValues = new ContentValues();
        taskValues.put(taskNameClmn, taskName);
        taskValues.put(subtasksNameClmn, convertArrToStr(subtasks));
        System.out.println(db.insert(tableName, null, taskValues));
        //db.execSQL("DROP TABLE " + tableName);
        // Note: logging
        db = taskDbAdapter.getReadableDatabase();
        HashMap<Integer,String> data = new HashMap<Integer,String>();
        Cursor cursor = db.query(tableName,
                new String[]{indexNameClmn, taskNameClmn, subtasksNameClmn},
                null, null, null, null, null);
        cursor.moveToFirst();
        System.out.println(cursor.getString(0));
        do {
            System.out.println("Elements: " + cursor.getString(0) + ", " + cursor.getString(1));
            data.put(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        } while (cursor.moveToNext());
        cursor.close();
        System.out.println("ids is " + data.keySet());
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
        db.close();

        // Note: logging
        System.out.println("WELL DONE");
    }

    /*public void deleteTaskByName(String taskName){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        db.delete(tableName,
                taskNameClmn + " = ?",
                new String[] {taskName});
    }*/

    public void deleteTaskByIndex(int index){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        db.delete(tableName,
                indexNameClmn + " = ?",
                new String[] {Integer.toString(index)});
        // Note: logging
        db = taskDbAdapter.getReadableDatabase();
        HashMap<String,String> data = new HashMap<>();
        Cursor cursor = db.query(tableName,
                new String[]{indexNameClmn, taskNameClmn},
                null, null, null, null, null);
        while (true) {
            if (cursor.moveToNext()) {
                data.put(cursor.getString(0), cursor.getString(1));
            } else
                break;
        }
        System.out.println(data.keySet());
        //////////////
        db.close();
        rewriteDB();

        // Note: logging
        db = taskDbAdapter.getReadableDatabase();
        /*HashMap<String,String>*/ data = new HashMap<>();
        /*Cursor*/ cursor = db.query(tableName,
                new String[]{indexNameClmn, taskNameClmn},
                null, null, null, null, null);
        while (true) {
            if (cursor.moveToNext()) {
                data.put(cursor.getString(0), cursor.getString(1));
            } else
                break;
        }
        System.out.println(data.keySet());
        System.out.println("May be deleted");
    }


    /*public HashMap<Integer,String[]> getTaskByName(String taskName){
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
    }*/

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

    public HashMap<Integer,String[]> getAllTasks(){
        HashMap<Integer,String[]> data = new HashMap<Integer, String[]>();
        SQLiteDatabase db = taskDbAdapter.getReadableDatabase();
        try {
            @SuppressLint("Recycle") Cursor cursor = db.query(tableName,
                    new String[]{indexNameClmn, taskNameClmn, subtasksNameClmn},
                    null, null, null, null, null);
            while(cursor.moveToNext()){
                data.put(Integer.parseInt(cursor.getString(0)),
                        (cursor.getString(1) + "," + cursor.getString(2)).split(","));
                System.out.println((cursor.getString(1)+ "," + cursor.getString(2)).split(","));
            }
        } catch (SQLiteException e){
            System.out.println("errror");
        }
        return data;
    }

    public ArrayList<String> getAllTasksNames(){
        ArrayList<String> taskNames = new ArrayList<String>();
        //try {
            HashMap<Integer, String[]> data = getAllTasks();
            System.out.println("ArrayList is " + taskNames);
            for (int id = 1; id <= data.size(); id++) {
                try {
                    System.out.println("There is our elements: " + data.get(id)[0]);
                    taskNames.add(data.get(id)[0].toString());
                } catch (Throwable e) {
                    System.out.println("Error in helper is " + e);
                }
            }
        /*} catch(Throwable e){
            System.out.println("Error in helper is " + e);
        }*/
        return taskNames;
    }

    private String convertArrToStr(Object[] arr){
        String str = "";
        for(int i = 0; i < arr.length; i++){
            str += arr.toString();
        }
        return str;
    }

    private String convertArrToStr(ArrayList<String> arr){
        String str = "";
        for(int i = 0; i < arr.size(); i++){
            str += arr.get(i).toString() + ",";
        }
        return str;
    }

   private void rewriteDB(){
        HashMap<Integer,String[]> data = new HashMap<>();
        data = getAllTasks();
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
        for(int id : data.keySet()){
            System.out.println(data.get(id)[0]);
            insertTask(data.get(id)[0], data.get(id));
        }
        System.out.println("trying to delete");
    }
}

