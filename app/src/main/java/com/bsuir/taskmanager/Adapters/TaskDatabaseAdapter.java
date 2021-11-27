package com.bsuir.taskmanager.Adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Adapter for SQLite database to work with application
 */
public class TaskDatabaseAdapter extends SQLiteOpenHelper {

    /**
     * Name of database
     */
    private static final String DB_NAME = "TaskDB";
    /**
     * Version of database
     */
    private static final int DB_VERSION = 2;
    /**
     * Name of table
     */
    private static final String tableName = "TASKS";
    /**
     * Column for tasks names
     */
    private static final String taskNameClmn = "TASK_NAME";
    /**
     * Columns for subtasks names arrays
     */
    private static final String subtasksNameClmn = "SUBTASKS";
    /**
     * Column for item indexes
     */
    private static final String indexNameClmn = "_id";

    /**
     * Database Adapter constructor
     * @param context context for activity
     */
    public TaskDatabaseAdapter(Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }

    /**
     * This method gets called when database is firstly created
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TASKS (" + indexNameClmn + " INTEGER PRIMARY KEY," +
                taskNameClmn + " TEXT," +
                subtasksNameClmn + " TEXT);");
    }

    /**
     * This method gets called when user sets database version to higher one
     * @param db database
     * @param oldVersion old version
     * @param newVersion new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion) {
            //db.execSQL("DROP TABLE " + tableName);
            db.execSQL("CREATE TABLE TASKS (" + indexNameClmn + " INTEGER PRIMARY KEY," +
                    taskNameClmn + " TEXT," +
                    subtasksNameClmn + " TEXT);");
        }
    }

    /**
     * This method gets called when user sets database version to lower one
     * @param db database
     * @param oldVersion old version
     * @param newVersion new version
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion) {
            db.execSQL("CREATE TABLE TASKS (" + indexNameClmn + " INTEGER PRIMARY KEY," +
                    taskNameClmn + " TEXT," +
                    subtasksNameClmn + " TEXT);");
        }
    }

    /**
     * Get tableName
     * @return tableName
     */
    public String getTableName(){ return tableName; }

    /**
     * Get taskNameClmn
     * @return taskNameClmn
     */
    public String getTaskNameClmn(){ return taskNameClmn; }

    /**
     * Get subtasksNameClmn
     * @return subtasksNameClmn
     */
    public String getSubtasksNameClmn(){ return subtasksNameClmn; }

    /**
     * get indexNameClmn
     * @return indexNameClmn
     */
    public String getIndexNameClmn(){ return indexNameClmn; }
}

