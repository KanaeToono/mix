package com.example.conga.tvo.databases.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.conga.tvo.constants.ConstantTaskManager;


public class TaskHelper extends SQLiteOpenHelper {
    private static String TAG = TaskHelper.class.getSimpleName();
    public TaskHelper(Context context) {
        super(context, ConstantTaskManager.DATABASE_NAME, null, ConstantTaskManager.DATABASE_VERSION);
        Log.d(TAG, "task manager constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(ConstantTaskManager.CREATE_TABLE);
            Log.d(TAG, "Create table successful!");
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "Create database is fails");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            sqLiteDatabase.execSQL(ConstantTaskManager.DROP_TABLE);
            onCreate(sqLiteDatabase);
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.d(TAG, "Upgrade table task manager  successful!");
    }
}
