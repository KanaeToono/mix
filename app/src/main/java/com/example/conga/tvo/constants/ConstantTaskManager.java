package com.example.conga.tvo.constants;

/**
 * Created by ConGa on 3/04/2016.
 */
public class ConstantTaskManager {
    private static String TAG = ConstantTaskManager.class.getSimpleName();
    // Constatns for TaskManager Database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "taskmanager.db";
    public static final String TABLE_NAME = "task";
    public static final String KEY_ID_TASK = "_id_task";
    public static final String KEY_SUBJECT_TASK = "_subject";
    public static final String KEY_DATE_START_TASK = "_date_start_task";
    public static final String KEY_TIME_START_TASK = "_time_start_task";
    public static final String KEY_DATE_END_TASK = "_date_end_task";
    public static final String KEY_TIME_END_TASK = "_time_end_task";
    public static final String KEY_DESCRIPTION_TASK = "_description";
    public static final String KEY_STATUS_TASK = "_status";
    public static final String KEY_PIORITY_TASK = "_piority";
    public static final String KEY_STATUS_ALARM_TASK = "_id_alarm";
    public static String TAG_LIST_TASK_FRAGMENT ="tag_list_tasks_frag";
    public static String TAG_EDIT_TASK_FRAGMENT ="tag_edit_task_frag";
    public static String TAG_ADD_TASK_FRAGMENT ="tag_add_task_frag";
    public static String TAG_NOTYFICATION_FRAGMENT ="tag_notyfication_frag";


    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + " ("
                    + KEY_ID_TASK + " integer primary key autoincrement, "
                    + KEY_SUBJECT_TASK + " text not null, "
                    + KEY_DATE_START_TASK + " DATE, "
                    + KEY_TIME_START_TASK + " text not null, "
                    + KEY_DATE_END_TASK + " DATE, "
                    + KEY_TIME_END_TASK + " text not null, "
                    + KEY_DESCRIPTION_TASK + " text not null,"
                    + KEY_PIORITY_TASK + " integer,"
                    + KEY_STATUS_TASK + " integer ,"
                    + KEY_STATUS_ALARM_TASK + " integer );";
    public static final String DROP_TABLE = "DROP TABLE IF EXIST" + TABLE_NAME;
    //// constants for RssItem Database

}
