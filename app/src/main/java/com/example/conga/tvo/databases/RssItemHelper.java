package com.example.conga.tvo.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.conga.tvo.interfaces.ICRUDDatabase;
import com.example.conga.tvo.models.RssItem;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RssItemHelper extends SQLiteOpenHelper implements ICRUDDatabase {
    private static String TAG = RssItemHelper.class.getSimpleName();
    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
   private static final String DATABASE_NAME = "thihocki";
    // Table Names
    private static final String TABLE_DAOTAO = "daotao";
    private static final String TABLE_KHOATIN = "khoatin";
    private static final String TABLE_OFFLINE= "offline";

    // tên cột chung
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "_title";
    private static final String KEY_LINK = "_link";
    private static final String KEY_PUBDATE = "_pub_date";
    private static final String KEY_CONTENT_HTML ="_content";

    private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    // Table Create Statements
    // Todo table create statement
    public static final String CREATE_TABLE_DAOTAO_NEWS = "CREATE TABLE " + TABLE_DAOTAO + "(" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT," +
            KEY_LINK + " TEXT," + KEY_PUBDATE + " DATE);";
    // Tag table create statement
    public static final String CREATE_TABLE_KHOATIN_NEWS = "CREATE TABLE " + TABLE_KHOATIN + "(" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT," +
            KEY_LINK + " TEXT," + KEY_PUBDATE + " DATE);";
// offline table
public static final String CREATE_TABLE_OFFLINE_NEWS = "CREATE TABLE " + TABLE_OFFLINE + "(" + KEY_ID +
        " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT," +
        KEY_LINK + " TEXT," + KEY_PUBDATE + " DATE," +KEY_CONTENT_HTML + "TEXT);";

    public RssItemHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, " CREATE NEWS DATABASE SUCCESS");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        try {
            db.execSQL(CREATE_TABLE_DAOTAO_NEWS);
            Log.d(TAG, "CREATE TABLE SUCCESS");
            db.execSQL(CREATE_TABLE_KHOATIN_NEWS);
  db.execSQL(CREATE_TABLE_OFFLINE_NEWS);


        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Lỗi rồi");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAOTAO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_KHOATIN);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE);
            // create new tables
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewItemRssDaoTao(RssItem rssItem) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TITLE, rssItem.getTitle());
            contentValues.put(KEY_LINK, rssItem.getLink());
            contentValues.put(KEY_PUBDATE, format.format(rssItem.getPubDate()));
            sqLiteDatabase.insert(TABLE_DAOTAO, null, contentValues);
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<RssItem> getAllItemsRssDaoTao() {
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        ArrayList<RssItem> contentRssArrayList = new ArrayList<RssItem>();
        try {

            String QUERY = " select * from " + TABLE_DAOTAO +" order by " + KEY_PUBDATE + " desc ";
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    RssItem item = new RssItem();
                    item.setId_rssItem(cursor.getInt(0));
                    item.setTitle(cursor.getString(1));

                    item.setLink(cursor.getString(2));
                    try {
                        item.setPubDate(format.parse(cursor.getString(3)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    contentRssArrayList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR LOAD TASK" + e + "");
        }
        // mDatabase.close();
        return contentRssArrayList;
    }

    public int getRssItemCount() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int num = 0;
        try {
            String QUERY = "SELECT * FROM " + TABLE_DAOTAO;
            Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
            num = cursor.getCount();
            sqLiteDatabase.close();
            return num;
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return 0;
    }


    public Date getRssItemLastPubDate() {
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        Date date = null;
        try {
            String QUERY = " SELECT * FROM " + TABLE_DAOTAO +
                    " ORDER BY " + KEY_PUBDATE + " DESC LIMIT 1";

            Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst()) {
                date = format.parse(cursor.getString(cursor.getColumnIndex(KEY_PUBDATE)));
                Log.d(TAG, date + "");
            }
            // mSqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }
    @Override
    public void deleteItemRssItemDaoTao(int id_rss) {

    }

    @Override
    public void addNewItemRssKhoaTin(RssItem rssItem) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TITLE, rssItem.getTitle());
            contentValues.put(KEY_LINK, rssItem.getLink());
            contentValues.put(KEY_PUBDATE, format.format(rssItem.getPubDate()));
            sqLiteDatabase.insert(TABLE_KHOATIN, null, contentValues);
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<RssItem> getAllItemsRssKhoaTin() {

        SQLiteDatabase mDatabase = this.getWritableDatabase();
        ArrayList<RssItem> contentRssArrayList = new ArrayList<RssItem>();
        try {

            String QUERY = " select * from " + TABLE_KHOATIN +" order by " + KEY_PUBDATE + " desc ";
            Cursor cursor = mDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
                do {
                    RssItem item = new RssItem();
                    item.setId_rssItem(cursor.getInt(0));
                    item.setTitle(cursor.getString(1));

                    item.setLink(cursor.getString(2));
                    try {
                        item.setPubDate(format.parse(cursor.getString(3)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    contentRssArrayList.add(item);
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
    public void deleteItemRssItemKhoaTin(int id_rss) {

    }
    public int getRssItemCountKhoaTin() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int num = 0;
        try {
            String QUERY = "SELECT * FROM " + TABLE_KHOATIN;
            Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
            num = cursor.getCount();
            sqLiteDatabase.close();
            return num;
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return 0;
    }


    public Date getRssItemLastPubDateKhoaTin() {
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        Date date = null;
        try {
            String QUERY = " SELECT * FROM " + TABLE_KHOATIN+
                    " ORDER BY " + KEY_PUBDATE + " DESC LIMIT 1";

            Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
            if (cursor.moveToFirst()) {
                date = format.parse(cursor.getString(cursor.getColumnIndex(KEY_PUBDATE)));
                Log.d(TAG, date + "");
            }
            // mSqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

//    @Override
//    public void (RssItem contentRss) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        try {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(KEY_TITLE, contentRss.getTitle());
//            contentValues.put(KEY_LINK, contentRss.getContent());
//            contentValues.put(KEY_IMAGE, contentRss.getImage());
//            database.insert(TABLE_OFFLINE_NEWS, null, contentValues);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("problem", e + "");
//        }
//    }

//    @Override
//    public ArrayList<ContentRss> getAllItemsContent() {
//        SQLiteDatabase mDatabase = this.getWritableDatabase();
//        ArrayList<ContentRss> contentRssArrayList = new ArrayList<ContentRss>();
//        try {
//            String QUERY = " SELECT * FROM " + TABLE_OFFLINE_NEWS;
//            Cursor cursor = mDatabase.rawQuery(QUERY, null);
//            if (cursor.moveToFirst() && cursor.getCount() >= 1) {
//                do {
//                    ContentRss task = new ContentRss();
//                    task.setId_content(cursor.getInt(0));
//                    task.setTitle(cursor.getString(1));
//
//                    task.setContent(cursor.getString(2));
//                    task.setImage(cursor.getString(3));
//
//                    contentRssArrayList.add(task);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG, "ERROR LOAD TASK" + e + "");
//        }
//        // mDatabase.close();
//        return contentRssArrayList;
//    }
//
//    @Override
//    public void deleteItemContent(int id_content) {
//        SQLiteDatabase mDatabase = this.getWritableDatabase();
//        try {
//            mDatabase.delete(TABLE_OFFLINE_NEWS, KEY_ID
//                    + " =" + id_content, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
public RssItemHelper open() throws SQLException {
    //   SQLiteDatabase.CursorFactory ctx;
    //   TaskDbAdapter  taskdb= new TaskDbAdapter(Context ctx);
    SQLiteDatabase data = this.getWritableDatabase();
    return this;
}

    // dong ket noi voi CSDL
    public void closeDatabase() {
        SQLiteDatabase database = getWritableDatabase();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

}
