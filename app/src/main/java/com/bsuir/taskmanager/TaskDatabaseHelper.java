package com.bsuir.taskmanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bsuir.taskmanager.Adapters.TaskDatabaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;


public class TaskDatabaseHelper {
    /**
     * Context for TaskDatabaseAdapter
     */
    private final Context context;
    /**
     * Object of TaskDatabaseAdapter to communicate with database
     */
    private final TaskDatabaseAdapter taskDbAdapter;
    /**
     * Object that stores tasks values
     */
    private ContentValues taskValues;
    /**
     * taskName column
     */
    private final String taskNameClmn;
    /**
     * subtasksName column
     */
    private final String subtasksNameClmn;
    /**
     * indexName column
     */
    private final String indexNameClmn;
    /**
     * Name of table
     */
    private final String tableName;

    /**
     * Constructor for TaskDatabaseHelper
     * @param context context for TaskDatabaseAdapter
     */
    public TaskDatabaseHelper(Context context){
        this.context = context;
        taskDbAdapter = new TaskDatabaseAdapter(this.context);
        taskNameClmn = taskDbAdapter.getTaskNameClmn();
        subtasksNameClmn = taskDbAdapter.getSubtasksNameClmn();
        indexNameClmn = taskDbAdapter.getIndexNameClmn();
        tableName = taskDbAdapter.getTableName();

//        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
//        db.execSQL("DROP TABLE " + tableName);
    }


    /**
     * Method to insert new task with string[]
     * @param taskName name of task
     * @param subtasks String[] of subtasks
     */
    public void insertTask(String taskName, String[] subtasks){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        taskValues = new ContentValues();
        taskValues.put(taskNameClmn, taskName);
        taskValues.put(subtasksNameClmn, convertArrToStr(subtasks));
        db.insert(tableName, null, taskValues);

        db.close();
    }

    /**
     * Method to insert new task with ArrayList
     * @param taskName name of task
     * @param subtasks ArrayList<String> of subtasks
     */
    public void insertTask(String taskName, ArrayList<String> subtasks){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        taskValues = new ContentValues();
        taskValues.put(taskNameClmn, taskName);
        taskValues.put(subtasksNameClmn, convertArrToStr(subtasks));
        System.out.println(db.insert(tableName, null, taskValues));
        //db.execSQL("DROP TABLE " + tableName);
        // Note: logging
        /*db = taskDbAdapter.getReadableDatabase();
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
        db.close();*/
    }

    /**
     * Update task by taskName in database
     * @param taskName name of task
     * @param subtasks array of subtasks
     */
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

    /**
     * Update task by id in database
     * @param index id
     * @param taskName name of task
     * @param subtasks array subtasks
     */
    public void updateTaskByIndex(int index, String taskName, ArrayList<String> subtasks){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        taskValues = new ContentValues();
        taskValues.put(taskNameClmn, taskName);
        taskValues.put(subtasksNameClmn, convertArrToStr(subtasks));
        db.update(tableName, taskValues, indexNameClmn + " = ?", new String[] {Integer.toString(index)});
        //db.execSQL("DROP TABLE " + tableName);
        db.close();
    }

    /*public void deleteTaskByName(String taskName){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        db.delete(tableName,
                taskNameClmn + " = ?",
                new String[] {taskName});
    }*/

    /**
     * Delete task by id
     * @param index id
     */
    public void deleteTaskByIndex(int index){
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        db.delete(tableName,
                indexNameClmn + " = ?",
                new String[] {Integer.toString(index)});
        db.close();
        rewriteDB();

        // Note: logging
        /*db = taskDbAdapter.getReadableDatabase();
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


        // Note: logging
        db = taskDbAdapter.getReadableDatabase();
        /*HashMap<String,String> data = new HashMap<>();
        /*Cursor cursor = db.query(tableName,
                new String[]{indexNameClmn, taskNameClmn},
                null, null, null, null, null);
        while (true) {
            if (cursor.moveToNext()) {
                data.put(cursor.getString(0), cursor.getString(1));
            } else
                break;
        }
        System.out.println(data.keySet());
        System.out.println("May be deleted");*/
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

    /**
     * Get task by id in HashMap
     * @param index id
     * @return HashMap<String, String[]>
     */
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

    /**
     * Get all tasks from database in HashMap
     * @return HashMap<Integer, String[]>
     */
    public HashMap<Integer,String[]> getAllTasks(){
        HashMap<Integer,String[]> data = new HashMap<>();
        SQLiteDatabase db = taskDbAdapter.getReadableDatabase();
        //db.execSQL("DROP TABLE " + tableName);
        try {
            @SuppressLint("Recycle") Cursor cursor = db.query(tableName,
                    new String[]{indexNameClmn, taskNameClmn, subtasksNameClmn},
                    null, null, null, null, null);
            while(cursor.moveToNext()){
                data.put(Integer.parseInt(cursor.getString(0)),
                        (cursor.getString(1) + "," + cursor.getString(2)).split(","));
            }
        } catch (SQLiteException e){
            System.out.println("errror");
        }
        return data;
    }

    /**
     * Method to get all taskNames from database in ArrayList<String>
     * @return ArrayList<String>
     */
    public ArrayList<String> getAllTasksNames(){
        ArrayList<String> taskNames = new ArrayList<>();
        HashMap<Integer, String[]> data = getAllTasks();
        for (int id = 1; id <= data.size(); id++) {
            System.out.println("There is our elements: " + Objects.requireNonNull(data.get(id))[0]);
            taskNames.add(Objects.requireNonNull(data.get(id))[0]);
        }
        return taskNames;
    }

    /**
     * Convert Object[] to String to store in database
     * @param arr Object[]
     * @return converted string
     */
    private String convertArrToStr(Object[] arr){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < arr.length; i++){
            str.append(Arrays.toString(arr));
        }
        return str.toString();
    }


    /**
     * Convert ArrayList<String> to String to store in database
     * @param arr array
     * @return converted string
     */
    private String convertArrToStr(ArrayList<String> arr){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < arr.size(); i++){
            str.append(arr.get(i)).append(",");
        }
        return str.toString();
    }

    /**
     * Method to rewrite database with new data hashmap
     */
    private void rewriteDB(){
        HashMap<Integer,String[]> data;
        data = getAllTasks();
        SQLiteDatabase db = taskDbAdapter.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
        for(int id : data.keySet()){
            System.out.println(Objects.requireNonNull(data.get(id))[0]);
            insertTask(Objects.requireNonNull(data.get(id))[0], data.get(id));
        }
    }
}

