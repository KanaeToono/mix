package com.example.conga.tvo.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.conga.tvo.interfaces.ICRUDDatabase;
import com.example.conga.tvo.models.ContentRss;
import com.example.conga.tvo.models.RssItem;

import java.sql.SQLException;
import java.util.ArrayList;

public class RssItemHelper extends SQLiteOpenHelper implements ICRUDDatabase {
    private static String TAG = RssItemHelper.class.getSimpleName();
        // Database Version
        private static final int DATABASE_VERSION = 2;
        // Database Name
        private static final String DATABASE_NAME = "newsDatabase";
        // Table Names
        private static final String TABLE_OFFLINE_NEWS = "offlinenews";
        private static final String TABLE_FAVORITES_NEWS = "favoritenews";

        // tên cột chung
        private static final String KEY_ID = "_id";
        private static final String KEY_TITLE= "_title";
         private static final String KEY_IMAGE = "_image";

        // Offline Table
        private static final String KEY_CONTENT_HTML= "_content";
        // Favorites Table - column names
        private static final String KEY_LINK= "_link";
       private static final String KEY_PUBDATE ="_pub_date";


        // Table Create Statements
        // Todo table create statement
        public  static final String CREATE_TABLE_OFFLINE_NEWS = "CREATE TABLE " + TABLE_OFFLINE_NEWS + "(" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT," +
                KEY_CONTENT_HTML + " TEXT," + KEY_IMAGE + " TEXT );";
        // Tag table create statement
        public  static final String CREATE_TABLE_FAVORITES_NEWS = "CREATE TABLE " + TABLE_FAVORITES_NEWS + "(" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," +KEY_TITLE + " TEXT," +
                KEY_LINK+ " TEXT," +KEY_PUBDATE+"TEXT, " +KEY_IMAGE +" TEXT ) ;";


        public RssItemHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.d(TAG, " CREATE NEWS DATABASE SUCCESS");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // creating required tables
            try {
                db.execSQL(CREATE_TABLE_OFFLINE_NEWS);
                Log.d(TAG, "CREATE TABLE SUCCESS");
                db.execSQL(CREATE_TABLE_FAVORITES_NEWS);


            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Lỗi rồi");
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // on upgrade drop older tables
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE_NEWS);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES_NEWS);
                // create new tables
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    @Override
    public void addNewItemContent(ContentRss contentRss) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TITLE, contentRss.getTitle());
            contentValues.put(KEY_CONTENT_HTML, contentRss.getContent());
            contentValues.put(KEY_IMAGE,contentRss.getImage());
            database.insert(TABLE_OFFLINE_NEWS, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("problem", e + "");
        }
    }

    @Override
    public ArrayList<ContentRss> getAllItemsContent() {
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        ArrayList<ContentRss> contentRssArrayList = new ArrayList<ContentRss>();
        try {
            String QUERY = " SELECT * FROM " + TABLE_OFFLINE_NEWS;
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    ContentRss task = new ContentRss();
                    task.setId_content(cursor.getInt(0));
                    task.setTitle(cursor.getString(1));

                    task.setContent(cursor.getString(2));
                    task.setImage(cursor.getString(3));

                    contentRssArrayList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
        // mDatabase.close();
        return contentRssArrayList;
    }

    @Override
    public void deleteItemContent(int id_content) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        try {
            mDatabase.delete(TABLE_OFFLINE_NEWS, KEY_ID
                    + " =" + id_content, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addNewItemRss(RssItem rssItem) {

    }

    @Override
    public ArrayList<RssItem> getAllItemsRss() {
        return null;
    }

    @Override
    public void deleteItemRssItem(int id_rss) {

    }
    public RssItemHelper open() throws SQLException {
        //   SQLiteDatabase.CursorFactory ctx;
        //   TaskDbAdapter  taskdb= new TaskDbAdapter(Context ctx);
        SQLiteDatabase data = this.getWritableDatabase();
        return this;
    }
    // dong ket noi voi CSDL
    public void closeDatabase() {
        SQLiteDatabase database = getWritableDatabase();

        if(database!=null && database.isOpen()) {
            database.close();
        }
    }
}
