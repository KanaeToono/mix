package com.example.conga.tvo.databases.databaseadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.conga.tvo.constants.ConstantTaskManager;
import com.example.conga.tvo.databases.databasehelper.TaskHelper;
import com.example.conga.tvo.interfaces.ITaskListenerInterface;
import com.example.conga.tvo.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class TaskDatabaseAdapter implements ITaskListenerInterface {
    private static String TAG = TaskDatabaseAdapter.class.getSimpleName();
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private TaskHelper mTaskHelper;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


    public TaskDatabaseAdapter(Context mContext) {
        this.mContext = mContext;
        mTaskHelper = new TaskHelper(mContext);
        Log.d(TAG, "");
    }

    // open database
    public TaskDatabaseAdapter openDB() {
        try {
            mDatabase = mTaskHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    // close database
    public void closeDB() {
        try {
            mTaskHelper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewTask(Task task) {
        try {
            ContentValues values = new ContentValues();
            values.put(ConstantTaskManager.KEY_SUBJECT_TASK, task.getSubject_task());
            values.put(ConstantTaskManager.KEY_DATE_START_TASK, format.format(task.getDate_start_task()));
            values.put(ConstantTaskManager.KEY_TIME_START_TASK, task.getTime_start_task());
            values.put(ConstantTaskManager.KEY_DATE_END_TASK, format.format(task.getDate_end_task()));
            values.put(ConstantTaskManager.KEY_TIME_END_TASK, task.getTime_end_task());
            values.put(ConstantTaskManager.KEY_DESCRIPTION_TASK, task.getDescription_task());
            values.put(ConstantTaskManager.KEY_PIORITY_TASK, task.getPiority_task());
            values.put(ConstantTaskManager.KEY_STATUS_TASK, task.getStatus_task());
            values.put(ConstantTaskManager.KEY_STATUS_ALARM_TASK, task.getAlarm_task());
            mDatabase.insert(ConstantTaskManager.TABLE_NAME, null, values);
            mDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR: " + e + "");
        }
    }

    @Override
    public void editTask(Task _task) {
        try {
            ContentValues values = new ContentValues();
            values.put(ConstantTaskManager.KEY_SUBJECT_TASK, _task.getSubject_task());
            values.put(ConstantTaskManager.KEY_DATE_START_TASK, format.format(_task.getDate_start_task()));
            values.put(ConstantTaskManager.KEY_TIME_START_TASK, _task.getTime_start_task());
            values.put(ConstantTaskManager.KEY_DATE_END_TASK, format.format(_task.getDate_end_task()));
            values.put(ConstantTaskManager.KEY_TIME_END_TASK, _task.getTime_end_task());
            values.put(ConstantTaskManager.KEY_DESCRIPTION_TASK, _task.getDescription_task());
            values.put(ConstantTaskManager.KEY_PIORITY_TASK, _task.getPiority_task());
            values.put(ConstantTaskManager.KEY_STATUS_TASK, _task.getStatus_task());
            values.put(ConstantTaskManager.KEY_STATUS_ALARM_TASK, _task.getAlarm_task());

            if (mDatabase.update(ConstantTaskManager.TABLE_NAME, values, ConstantTaskManager.KEY_ID_TASK
                    + " = " + _task.getId_task(), null) > 0) {
                Log.d(TAG, "CAN NOT UPDATE");
            } else {
                Log.d(TAG, "update success");
            }
        //     mDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Task> getAllTask() {
        ArrayList<Task> tasksList = new ArrayList<Task>();
        try {
            String QUERY = " SELECT * FROM " + ConstantTaskManager.TABLE_NAME;
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    Task task = new Task();
                    task.setId_task(cursor.getInt(0));
                    task.setSubject_task(cursor.getString(1));
                    task.setDate_start_task(format.parse(cursor.getString(2)));
                    task.setTime_start_task(cursor.getString(3));
                    task.setDate_end_task(format.parse(cursor.getString(4)));
                    task.setTime_end_task(cursor.getString(5));
                    task.setDescription_task(cursor.getString(6));
                    task.setPiority_task(cursor.getInt(7));
                    task.setStatus_task(cursor.getInt(8));
                    task.setAlarm_task(cursor.getInt(9));
                    tasksList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
       // mDatabase.close();
        return tasksList;
    }

    @Override
    public ArrayList<Task> sortAllTaskDeadlineDay(Task task) {
        return null;
    }

    @Override
    public void deleteTask(int id_task) {
        try {
            mDatabase.delete(ConstantTaskManager.TABLE_NAME, ConstantTaskManager.KEY_ID_TASK
                    + " =" + id_task, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean updateTask(Task task) {
        return false;
    }

    @Override
    public void upDateStatus(int status, int id_task) {
        ContentValues values = new ContentValues();
        values.put(ConstantTaskManager.KEY_STATUS_ALARM_TASK, status);
        try {
            if (mDatabase.update(ConstantTaskManager.TABLE_NAME, values, ConstantTaskManager.KEY_ID_TASK + "=" + id_task, null) > 0)
                Log.d(TAG, "UPDATE stt SUCCESS");
            else
                Log.d(TAG, "FAIL");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void upDateStatusDoneTask(int status, int id_task) {
        ContentValues values = new ContentValues();
        values.put(ConstantTaskManager.KEY_STATUS_TASK, status);
        try {
            if (mDatabase.update(ConstantTaskManager.TABLE_NAME, values, ConstantTaskManager.KEY_ID_TASK + "=" + id_task, null) > 0)
                Log.d(TAG, "UPDATE stt SUCCESS");
            else
                Log.d(TAG, "FAIL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Task> getTaskToday() {
        ArrayList<Task> tasksList = new ArrayList<Task>();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateForButton = sdf.format(cal.getTime());
        try {
            String QUERY = " select * from " + ConstantTaskManager.TABLE_NAME + " where " +
                    ConstantTaskManager.KEY_DATE_START_TASK + "='" + dateForButton + "'";
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    Task task = new Task();
                    task.setId_task(cursor.getInt(0));
                    task.setSubject_task(cursor.getString(1));
                    task.setDate_start_task(format.parse(cursor.getString(2)));
                    task.setTime_start_task(cursor.getString(3));
                    task.setDate_end_task(format.parse(cursor.getString(4)));
                    task.setTime_end_task(cursor.getString(5));
                    task.setDescription_task(cursor.getString(6));
                    task.setPiority_task(cursor.getInt(7));
                    task.setStatus_task(cursor.getInt(8));
                    task.setAlarm_task(cursor.getInt(9));
                    tasksList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
        return tasksList;
    }

    @Override
    public ArrayList<Task> getCompleteTasks() {
        ArrayList<Task> tasksList = new ArrayList<Task>();


        try {
            String QUERY = " SELECT * FROM " + ConstantTaskManager.TABLE_NAME + " WHERE " + ConstantTaskManager.KEY_STATUS_TASK
                    + "=1";
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    Task task = new Task();
                    task.setId_task(cursor.getInt(0));
                    task.setSubject_task(cursor.getString(1));
                    task.setDate_start_task(format.parse(cursor.getString(2)));
                    task.setTime_start_task(cursor.getString(3));
                    task.setDate_end_task(format.parse(cursor.getString(4)));
                    task.setTime_end_task(cursor.getString(5));
                    task.setDescription_task(cursor.getString(6));
                    task.setPiority_task(cursor.getInt(7));
                    task.setStatus_task(cursor.getInt(8));
                    task.setAlarm_task(cursor.getInt(9));
                    tasksList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
        return tasksList;
    }

    @Override
    public ArrayList<Task> getIncompleteTasks() {
        ArrayList<Task> tasksList = new ArrayList<Task>();

        try {
            String QUERY = " SELECT * FROM " + ConstantTaskManager.TABLE_NAME + " WHERE " + ConstantTaskManager.KEY_STATUS_TASK
                    + "=0";
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    Task task = new Task();
                    task.setId_task(cursor.getInt(0));
                    task.setSubject_task(cursor.getString(1));
                    task.setDate_start_task(format.parse(cursor.getString(2)));
                    task.setTime_start_task(cursor.getString(3));
                    task.setDate_end_task(format.parse(cursor.getString(4)));
                    task.setTime_end_task(cursor.getString(5));
                    task.setDescription_task(cursor.getString(6));
                    task.setPiority_task(cursor.getInt(7));
                    task.setStatus_task(cursor.getInt(8));
                    task.setAlarm_task(cursor.getInt(9));
                    tasksList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
        return tasksList;
    }

    @Override
    public ArrayList<Task> getImportantTask() {
        ArrayList<Task> tasksList = new ArrayList<Task>();


        try {
            String QUERY = " SELECT * FROM " + ConstantTaskManager.TABLE_NAME + " WHERE " + ConstantTaskManager.KEY_PIORITY_TASK
                    + "=1";
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    Task task = new Task();
                    task.setId_task(cursor.getInt(0));
                    task.setSubject_task(cursor.getString(1));
                    task.setDate_start_task(format.parse(cursor.getString(2)));
                    task.setTime_start_task(cursor.getString(3));
                    task.setDate_end_task(format.parse(cursor.getString(4)));
                    task.setTime_end_task(cursor.getString(5));
                    task.setDescription_task(cursor.getString(6));
                    task.setPiority_task(cursor.getInt(7));
                    task.setStatus_task(cursor.getInt(8));
                    task.setAlarm_task(cursor.getInt(9));
                    tasksList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
        return tasksList;
    }

    @Override
    public ArrayList<Task> sortPiorityTask() {
        ArrayList<Task> tasksList = new ArrayList<Task>();
        try {
            String QUERY = " select * from " + ConstantTaskManager.TABLE_NAME + " order by " +
                    ConstantTaskManager.KEY_PIORITY_TASK + " desc ";
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    Task task = new Task();
                    task.setId_task(cursor.getInt(0));
                    task.setSubject_task(cursor.getString(1));
                    task.setDate_start_task(format.parse(cursor.getString(2)));
                    task.setTime_start_task(cursor.getString(3));
                    task.setDate_end_task(format.parse(cursor.getString(4)));
                    task.setTime_end_task(cursor.getString(5));
                    task.setDescription_task(cursor.getString(6));
                    task.setPiority_task(cursor.getInt(7));
                    task.setStatus_task(cursor.getInt(8));
                    task.setAlarm_task(cursor.getInt(9));
                    tasksList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
        return tasksList;
    }

    @Override
    public ArrayList<Task> sortDeadLineDayTask() {
        ArrayList<Task> tasksList = new ArrayList<Task>();
        try {
            String QUERY = " select * from " + ConstantTaskManager.TABLE_NAME + " order by " +
                    ConstantTaskManager.KEY_DATE_END_TASK + " asc ";
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    Task task = new Task();
                    task.setId_task(cursor.getInt(0));
                    task.setSubject_task(cursor.getString(1));
                    task.setDate_start_task(format.parse(cursor.getString(2)));
                    task.setTime_start_task(cursor.getString(3));
                    task.setDate_end_task(format.parse(cursor.getString(4)));
                    task.setTime_end_task(cursor.getString(5));
                    task.setDescription_task(cursor.getString(6));
                    task.setPiority_task(cursor.getInt(7));
                    task.setStatus_task(cursor.getInt(8));
                    task.setAlarm_task(cursor.getInt(9));
                    tasksList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
        return tasksList;
    }

    @Override
    public ArrayList<Task> sortStartDateTask() {
        ArrayList<Task> tasksList = new ArrayList<Task>();
        try {
            String QUERY = " select * from " + ConstantTaskManager.TABLE_NAME + " order by " +
                    ConstantTaskManager.KEY_DATE_START_TASK + " asc ";
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    Task task = new Task();
                    task.setId_task(cursor.getInt(0));
                    task.setSubject_task(cursor.getString(1));
                    task.setDate_start_task(format.parse(cursor.getString(2)));
                    task.setTime_start_task(cursor.getString(3));
                    task.setDate_end_task(format.parse(cursor.getString(4)));
                    task.setTime_end_task(cursor.getString(5));
                    task.setDescription_task(cursor.getString(6));
                    task.setPiority_task(cursor.getInt(7));
                    task.setStatus_task(cursor.getInt(8));
                    task.setAlarm_task(cursor.getInt(9));
                    tasksList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
        return tasksList;
    }


}
