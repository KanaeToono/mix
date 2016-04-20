package com.example.conga.tvo.constants;

/**
 * Created by ConGa on 3/04/2016.
 */
public class ConstantRssItem {
    public static String TAG = ConstantRssItem.class.getSimpleName();
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "newsdatabase.db";
    //TABLE FOR OFFLINE CATEGORY
    public static final String TABLE_NAME_OFFLINE_NEWS = "OfflineNews";
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE_OFFLINE = "_title";
    public static final String KEY_CONTENT_HTML= "_content";
    public static final String KEY_IMAGE_OFFLINE = "_image";
  // TABLE FOR FAVORITES CATEGORY
  public static final String TABLE_NAME_FAVORITES_NEWS = "favoriteNews";
    public static final String KEY_ID_FAVORITES = "_id";
    public static final String KEY_TITLE_FAVORITES = "_title";
    public static final String KEY_LINK= "_link";
    public static final String KEY_PUBDATE ="_pub_date";
    public static final String KEY_IMAGE_FAVORITES = "_image";
    // create table offline news
    public  static final String CREATE_TABLE_OFFLINE_NEWS = "CREATE TABLE " + TABLE_NAME_OFFLINE_NEWS + " (" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE_OFFLINE + " TEXT(100)," +
            KEY_CONTENT_HTML + " TEXT(100)," + KEY_IMAGE_OFFLINE +"TEXT(100));";
    //drop table offline news
    public static final String DROP_TABLE_OFFLINE_NEWS = "DROP TABLE IF EXISTS " + TABLE_NAME_OFFLINE_NEWS;
    // create table favorites news
    public  static final String CREATE_TABLE_FAVORITES_NEWS = "CREATE TABLE " + TABLE_NAME_FAVORITES_NEWS + " (" + KEY_ID_FAVORITES +
            " INTEGER PRIMARY KEY AUTOINCREMENT," +KEY_TITLE_FAVORITES + " TEXT(100)," +
            KEY_LINK+ " TEXT(100)," +KEY_PUBDATE+"TEXT(100), " +KEY_IMAGE_FAVORITES +"TEXT(100));";
    //drop table favorites news
    public static final String DROP_TABLE_FAVORITES_NEWS = "DROP TABLE IF EXISTS " +TABLE_NAME_FAVORITES_NEWS;
}
