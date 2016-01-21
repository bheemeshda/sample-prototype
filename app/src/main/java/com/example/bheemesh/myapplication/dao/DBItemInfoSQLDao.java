/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */
package com.example.bheemesh.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.Utils.Utils;
import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.beans.Location;

import java.util.ArrayList;

/**
 * Groups Dao handling.
 *
 * @author bheemesh on 7/2/2015.
 */
public class DBItemInfoSQLDao implements DBItemInfoDao {

    private DBSQLiteHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;
    private String baseGroupVersion = Constants.EMPTY_STRING + 0;

    public DBItemInfoSQLDao(Context context) {
        this.context = context;
        dbHelper = DBSQLiteHelper.getInstance(context);
    }

    @Override
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public ArrayList<BaseItem> getBaseItems(String itemString) {

        Cursor cursor = null;
        ArrayList<BaseItem> itemsList = new ArrayList<BaseItem>();
        try {
            String query = "select * from " + dbHelper.TABLE_BASE_ITEMS + " where " + DBSQLiteConstants.NEWS_NAME
                    + " " +
                    "like " + "'%" + itemString + "%'";
            System.out.println("BHEEM : DatabaseTable : getWordMatches : query : " + query);
            cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return null;
            }
            System.out.println("BHEEM : DatabaseTable : getCount : " + cursor.getCount());
            if (!cursor.moveToFirst())
                cursor.moveToFirst();
            do {
                BaseItem item = getBaseItem(cursor);
                if (item != null) {
                    itemsList.add(item);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BHEEM : DatabaseTable : getWordMatches : Exception : " + e.getMessage());
        }
        return itemsList;
    }

    @Override
    public ArrayList<BaseItem> getAllBaseItems() {
        Cursor cursor = null;
        ArrayList<BaseItem> itemList = new ArrayList<BaseItem>();
        try {
            String query = "select * from " + dbHelper.TABLE_BASE_ITEMS;
            System.out.println("BHEEM : DatabaseTable : getWordMatches : query : " + query);
            cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return null;
            }
            System.out.println("BHEEM : DatabaseTable : getCount : " + cursor.getCount());
            if (!cursor.moveToFirst())
                cursor.moveToFirst();
            do {
                BaseItem item = getBaseItem(cursor);
                if (item != null) {
                    itemList.add(item);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            System.out.println("BHEEM : DatabaseTable : getWordMatches : Exception : " + e.getMessage());
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public ArrayList<BaseItem> getRecentBaseItems() {
        Cursor cursor = null;
        ArrayList<BaseItem> itemsList = new ArrayList<BaseItem>();
        try {
            String selectQuery = "select * from " + dbHelper.TABLE_BASE_ITEMS + " where " + DBSQLiteConstants
                    .BASE_ITEMS_TIME_ACCESS + " != 0";
            System.out.println("BHEEM : DatabaseTable : getRecentLocationItems : query : " + selectQuery);
            cursor = dbHelper.getReadableDatabase().rawQuery(selectQuery, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return null;
            }
            System.out.println("BHEEM : DatabaseTable : getCount : " + cursor.getCount());
            if (!cursor.moveToFirst())
                cursor.moveToFirst();
            do {
                BaseItem item = getBaseItem(cursor);
                if (item != null) {
                    itemsList.add(item);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            System.out.println("BHEEM : DatabaseTable : getWordMatches : Exception : " + e.getMessage());
            e.printStackTrace();
        }
        return itemsList;
    }

    @Override
    public ArrayList<BaseItem> getFavouritesBaseItems() {
        Cursor cursor = null;
        ArrayList<BaseItem> itemList = new ArrayList<BaseItem>();
        try {
            String query = "select * from " + dbHelper.TABLE_BASE_ITEMS + " where " + DBSQLiteConstants
                    .BASE_ITEMS_FAVORITE + " IS '1'";
            System.out.println("BHEEM : DatabaseTable : getWordMatches : query : " + query);
            cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return null;
            }
            System.out.println("BHEEM : DatabaseTable : getCount : " + cursor.getCount());
            if (!cursor.moveToFirst())
                cursor.moveToFirst();
            do {
                BaseItem item = getBaseItem(cursor);
                if (item != null) {
                    itemList.add(item);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            System.out.println("BHEEM : DatabaseTable : getWordMatches : Exception : " + e.getMessage());
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public void updateNewsItems(String itemVersion, ArrayList<BaseItem> baseItems) {
        BaseItem item;
        for (int i = 0; i < baseItems.size(); i++) {
            item = baseItems.get(i);
            updateNews(item);
        }
    }

    @Override
    public void updateNews(BaseItem baseItem) {
        ContentValues values = setBaseItemContentValues(baseItem);
        try {
            if (database.update(dbHelper.TABLE_BASE_ITEMS, values, DBSQLiteConstants.NEWS_ID + " = '" +
                    baseItem.getId() + "'", null) == 0) {
                long rowId = database.insert(dbHelper.TABLE_BASE_ITEMS, null, values);
            }
        } catch (Exception e) {
            System.out.println("BHEEM : DBBaseItemInfoSQLDao : updateBaseItem :" + e.getMessage());
            e.printStackTrace();
        }
    }


    private BaseItem getBaseItem(Cursor cursor) {
        BaseItem item = new BaseItem();
        if (null == cursor || cursor.getCount() <= 0) {
            return item;
        }

        try {
            ContentValues cv = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(cursor, cv);
            item.setId(cv.getAsString(DBSQLiteConstants.NEWS_ID));
            item.setName(cv.getAsString(DBSQLiteConstants.NEWS_NAME));
            item.setSubName((cv.getAsString(DBSQLiteConstants.NEWS_SUB_NAME)));
            item.setDescription(cv.getAsString(DBSQLiteConstants.NEWS_DESCRIPTION));
            item.setImageUrl(cv.getAsString(DBSQLiteConstants.NEWS_IMAGE_URL));

            item.setLocationId(cv.getAsString(DBSQLiteConstants.NEWS_LOCATION_ID));
            item.setCatKey((cv.getAsString(DBSQLiteConstants.NEWS_TOPIC_KEY)));

            item.setRating(cv.getAsString(DBSQLiteConstants.BASE_ITEMS_RATING));
            item.setTimeAccess(cv.getAsLong(DBSQLiteConstants.BASE_ITEMS_TIME_ACCESS));
            item.setIsFavorite(cv.getAsString(DBSQLiteConstants.BASE_ITEMS_FAVORITE).equals("1") ? true : false);
            item.setFavouriteAddedTime(cv.getAsLong(DBSQLiteConstants.BASE_ITEMS_FAVORITE_ADDED_TIME));
            Location location = setLocation(item.getLocationId());
            System.out.println("BHEEM : DBItemInfoSQLDao : getBaseItem : Location.id : "+location.getId());
            if (location != null) {
                item.setLocation(location);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BHEEM : DBBaseItemInfoSQLDao : getBaseItem :" + e.getMessage());
        }
        return item;
    }

    private ContentValues setBaseItemContentValues(BaseItem baseItem) {

        ContentValues values = new ContentValues();
        values.put(DBSQLiteConstants.NEWS_ID, baseItem.getId());
        values.put(DBSQLiteConstants.NEWS_NAME, baseItem.getName());
        values.put(DBSQLiteConstants.NEWS_SUB_NAME, baseItem.getSubName());
        values.put(DBSQLiteConstants.NEWS_DESCRIPTION, baseItem.getDescription());
        values.put(DBSQLiteConstants.NEWS_IMAGE_URL, baseItem.getImageUrl());
        values.put(DBSQLiteConstants.NEWS_TOPIC_KEY, baseItem.getCatKey());
        values.put(DBSQLiteConstants.BASE_ITEMS_RATING, baseItem.getRating());
        values.put(DBSQLiteConstants.BASE_ITEMS_TIME_ACCESS, baseItem.getTimeAccess());
        values.put(DBSQLiteConstants.BASE_ITEMS_FAVORITE, baseItem.isFavorite());
        values.put(DBSQLiteConstants.BASE_ITEMS_FAVORITE_ADDED_TIME, baseItem.getFavouriteAddedTime());

        //Updating the locationId
        String locationId = getLocationId(baseItem.getLocation());
        System.out.println("BHEEM : DBItemInfoSQLDao : setBaseItemContentValues : Location.id : "+locationId);
        values.put(DBSQLiteConstants.NEWS_LOCATION_ID, locationId);
        return values;
    }

    private String getLocationId(Location location) {
        DBLocationInfoSQLDao database = new DBLocationInfoSQLDao(Utils.getApplication());
        database.open();
        return database.updateLocation(location);
    }

    private Location setLocation(String locationId) {
        DBLocationInfoSQLDao database = new DBLocationInfoSQLDao(Utils.getApplication());
        database.open();
        return database.getLocation(locationId);
    }
}
