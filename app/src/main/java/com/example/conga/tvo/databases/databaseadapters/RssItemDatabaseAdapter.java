package com.example.conga.tvo.databases.databaseadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.conga.tvo.constants.ConstantRssItem;
import com.example.conga.tvo.databases.databasehelper.RssItemHelper;
import com.example.conga.tvo.models.ContentRss;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class RssItemDatabaseAdapter  {
    private static String TAG = RssItemDatabaseAdapter.class.getSimpleName();
    private RssItemHelper mRssItemHelper;
    private SQLiteDatabase mSqLiteDatabase;
    private Context mContext;
    private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    public RssItemDatabaseAdapter(Context mContext) {
        this.mContext = mContext;
        mRssItemHelper = new RssItemHelper(mContext);
        Log.d(TAG, "");
    }

    // open database
    public RssItemDatabaseAdapter openDB() {
        try {
            mSqLiteDatabase = mRssItemHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "LOI ");
        }
        return this;
    }

    // close database
    public void closeDB() {
        try {

            mRssItemHelper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRssItem(ContentRss rssItemPDT) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ConstantRssItem.KEY_TITLE, rssItemPDT.getTitle());
            contentValues.put(ConstantRssItem.KEY_CONTENT_HTML, rssItemPDT.getContent());
            mSqLiteDatabase.insert(ConstantRssItem.TABLE_NAME, null, contentValues);
            mSqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("problem", e + "");
        }
    }


}

