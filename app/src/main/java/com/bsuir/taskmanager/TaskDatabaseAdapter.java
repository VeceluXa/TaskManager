package com.bsuir.taskmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class for SQLite to connect with device
 */
public class TaskDatabaseAdapter extends SQLiteOpenHelper {

    private static final String DB_NAME = "TaskDB";
    private static final int DB_VERSION = 2;

    private static final String tableName = "TASKS";
    private static final String taskNameClmn = "TASK_NAME";
    private static final String subtasksNameClmn = "SUBTASKS";
    private static final String indexNameClmn = "_id";

    TaskDatabaseAdapter(Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("HO");
        db.execSQL("CREATE TABLE TASKS (" + indexNameClmn + " INTEGER PRIMARY KEY," +
                taskNameClmn + " TEXT," +
                subtasksNameClmn + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion) {
            System.out.println("GO");
            //db.execSQL("DROP TABLE " + tableName);
            db.execSQL("CREATE TABLE TASKS (" + indexNameClmn + " INTEGER PRIMARY KEY," +
                    taskNameClmn + " TEXT," +
                    subtasksNameClmn + " TEXT);");
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion) {
            db.execSQL("CREATE TABLE TASKS (" + indexNameClmn + " INTEGER PRIMARY KEY," +
                    taskNameClmn + " TEXT," +
                    subtasksNameClmn + " TEXT);");
        }
    }

    public String getTableName(){ return tableName; }

    public String getTaskNameClmn(){ return taskNameClmn; }

    public String getSubtasksNameClmn(){ return subtasksNameClmn; }

    public String getIndexNameClmn(){ return indexNameClmn; }
}

