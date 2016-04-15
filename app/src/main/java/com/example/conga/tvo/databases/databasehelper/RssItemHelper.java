package com.example.conga.tvo.databases.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.conga.tvo.constants.ConstantRssItem;

public class RssItemHelper extends SQLiteOpenHelper {
    private static String TAG = RssItemHelper.class.getSimpleName();

    public RssItemHelper(Context context) {
        super(context, ConstantRssItem.DB_NAME, null, ConstantRssItem.DB_VERSION);
        Log.d(TAG, " create rssItem database ");
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(ConstantRssItem.CREATE_TABLE);
            Log.d(TAG, ConstantRssItem.TABLE_NAME);
            Log.d(TAG, "CREATE TABLE rss item SUCESSFULLY");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " can not create table");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            sqLiteDatabase.execSQL(ConstantRssItem.DROP_TABLE);
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
