package com.example.conga.tvo.constants;

/**
 * Created by ConGa on 3/04/2016.
 */
public class ConstantRssItem {
    public static String TAG = ConstantRssItem.class.getSimpleName();
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Offlinenews.db";
    public static final String TABLE_NAME = "OfflineNews_table";
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "_title";
    public static final String KEY_CONTENT_HTML= "_content";
   // public static final String KEY_IMAGE = "_image";



    public  static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT(100)," +
            KEY_CONTENT_HTML + " TEXT(100)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
