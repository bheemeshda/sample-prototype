/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */
package com.example.bheemesh.myapplication.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author bheemesh
 */
public class DBSQLiteHelper extends SQLiteOpenHelper {
    //---------------- TABLE NAMES -------------------
    public static final String TABLE_BASE_ITEMS = "NEWS";
    public static final String TABLE_LOCATION = "LOCATION";
    public static final String TABLE_CATEGORY = "CATEGORY";

    public static String SQLITE_DB_NAME = "bheem.places";
    public static int SQLITE_DB_VERSION = 1;
    private static volatile DBSQLiteHelper instance;

    private DBSQLiteHelper(Context context) {
        super(context, SQLITE_DB_NAME, null, SQLITE_DB_VERSION);
    }

    public static DBSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DBSQLiteHelper.class) {
                if (instance == null) {
                    instance = new DBSQLiteHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getNewsTableSQL());
        db.execSQL(getLocationTable());
        db.execSQL(getTopicTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    private String getNewsTableSQL() {
        return "CREATE TABLE IF NOT EXISTS "
                + TABLE_BASE_ITEMS + "(" + DBSQLiteConstants.NEWS_ID
                + " integer primary key autoincrement," + DBSQLiteConstants.NEWS_NAME
                + " text," + DBSQLiteConstants.NEWS_SUB_NAME
                + " text," + DBSQLiteConstants.NEWS_DESCRIPTION
                + " text," + DBSQLiteConstants.NEWS_IMAGE_URL
                + " text," + DBSQLiteConstants.NEWS_LANDMARK
                + " text," + DBSQLiteConstants.NEWS_TOPIC_KEY

                + " text," + DBSQLiteConstants.NEWS_LOCATION_ID
                + " text," + DBSQLiteConstants.BASE_ITEMS_RATING
                + " text," + DBSQLiteConstants.BASE_ITEMS_TIME_ACCESS
                + " text," + DBSQLiteConstants.BASE_ITEMS_FAVORITE
                + " boolean," + DBSQLiteConstants.BASE_ITEMS_FAVORITE_ADDED_TIME
                + " long)";
    }

    private String getLocationTable() {
        return "CREATE TABLE IF NOT EXISTS "
                + TABLE_LOCATION + "(" + DBSQLiteConstants.LOCATION_ID
                + " integer primary key autoincrement," + DBSQLiteConstants.LOCATION_COUNTRY
                + " text," + DBSQLiteConstants.LOCATION_STATE
                + " text," + DBSQLiteConstants.LOCATION_DISTRICT
                + " text," + DBSQLiteConstants.LOCATION_TALUK
                + " text," + DBSQLiteConstants.LOCATION_LAT
                + " text," + DBSQLiteConstants.LOCATION_LONG
                + " text," + DBSQLiteConstants.LOCATION_LANDMARK
                + " text)";
    }

    private String getTopicTable() {
        return "CREATE TABLE IF NOT EXISTS "
                + TABLE_CATEGORY + "(" + DBSQLiteConstants.TOPIC_ID
                + " integer primary key autoincrement," + DBSQLiteConstants.TOPIC_KEY
                + " text," + DBSQLiteConstants.TOPIC_NAME_ENG
                + " text," + DBSQLiteConstants.TOPIC_DISPLAY_ENG
                + " text," + DBSQLiteConstants.TOPIC_IMG_LINK
                + " text," + DBSQLiteConstants.TOPIC_IS_OPTIONAL
                + " text," + DBSQLiteConstants.TOPIC_DATA_LINK
                + " text)";
    }
}
