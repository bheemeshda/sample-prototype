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
import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.beans.Location;

import java.util.ArrayList;

/**
 * Groups Dao handling.
 *
 * @author bheemesh on 7/2/2015.
 */
public class DBLocationInfoSQLDao implements DBLocationInfoDao {

    private DBSQLiteHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    public DBLocationInfoSQLDao(Context context) {
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
    public ArrayList<Location> getLocationItems(String itemString) {

        Cursor cursor = null;
        ArrayList<Location> locationList = new ArrayList<Location>();
        try {
            //TODO(bheemesh) need to change depending on the country/state/village/nearby. remove
            // locationId
            String query = "select * from " + dbHelper.TABLE_LOCATION + " where " + DBSQLiteConstants.LOCATION_ID
                    + " " + "like " + "'%" + itemString + "%'";
            System.out.println("BHEEM : DBLocationInfoSQLDao : getLocationItems : query : " + query);
            cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return null;
            }
            System.out.println("BHEEM : DBLocationInfoSQLDao : getCount : " + cursor.getCount());
            if (!cursor.moveToFirst())
                cursor.moveToFirst();
            do {
                Location item = getLocationItem(cursor);
                if (item != null) {
                    locationList.add(item);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BHEEM : DBLocationInfoSQLDao : getLocationItems : Exception : " + e.getMessage());
        }
        return locationList;
    }

    @Override
    public ArrayList<Location> getAllLocationItems() {
        Cursor cursor = null;
        ArrayList<Location> itemList = new ArrayList<Location>();
        try {
            String query = "select * from " + dbHelper.TABLE_LOCATION;
            System.out.println("BHEEM : DBLocationInfoSQLDao : getAllLocationItems : query : " + query);
            cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return null;
            }
            System.out.println("BHEEM : DBLocationInfoSQLDao : getCount : " + cursor.getCount());
            if (!cursor.moveToFirst())
                cursor.moveToFirst();
            do {
                Location item = getLocationItem(cursor);
                if (item != null) {
                    itemList.add(item);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            System.out.println("BHEEM : DBLocationInfoSQLDao : getAllLocationItems : Exception : " + e.getMessage());
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public Location getLocation(String locationId) {
        Cursor cursor = null;
        Location item = null;
        try {
            String query = "select * from " + dbHelper.TABLE_LOCATION + " where " + DBSQLiteConstants.LOCATION_ID
                    + " = '" + locationId + "'";
            System.out.println("BHEEM : DBLocationInfoSQLDao : getLocation : query : " + query);
            cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return null;
            }
            System.out.println("BHEEM : DBLocationInfoSQLDao : getCount : " + cursor.getCount());
            if (!cursor.moveToFirst())
                cursor.moveToFirst();
            do {
                item = getLocationItem(cursor);
                if (item != null) {
                    return item;
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BHEEM : DBLocationInfoSQLDao : getWordMatches : Exception : " + e.getMessage());
        }
        return item;
    }

    @Override
    public String updateLocation(Location location) {
        ContentValues values = setLocationContentValues(location);
        String insertedId = Constants.EMPTY_STRING;
        try {
            if (database.update(dbHelper.TABLE_LOCATION, values, DBSQLiteConstants.LOCATION_ID + " = '" +
                    location.getId() + "'", null) == 0) {
                long rowId = database.insert(dbHelper.TABLE_LOCATION, null, values);
                insertedId = ""+rowId;
            }
        } catch (Exception e) {
            System.out.println("BHEEM : DBLocationInfoSQLDao : updateLocation :" + e.getMessage());
            e.printStackTrace();
        }
        return insertedId;
    }


    private Location getLocationItem(Cursor cursor) {
        Location location = new Location();
        if (null == cursor || cursor.getCount() <= 0) {
            return location;
        }
        try {
            ContentValues cv = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(cursor, cv);
            location.setId(cv.getAsString(DBSQLiteConstants.LOCATION_ID));
            location.setCountry(cv.getAsString(DBSQLiteConstants.LOCATION_COUNTRY));
            location.setState((cv.getAsString(DBSQLiteConstants.LOCATION_STATE)));
            location.setDistrict(cv.getAsString(DBSQLiteConstants.LOCATION_DISTRICT));
            location.setTaluk(cv.getAsString(DBSQLiteConstants.LOCATION_TALUK));
            location.setLatitude(cv.getAsString(DBSQLiteConstants.LOCATION_LAT));
            location.setLongitude((cv.getAsString(DBSQLiteConstants.LOCATION_LONG)));
            location.setLandMark((cv.getAsString(DBSQLiteConstants.LOCATION_LANDMARK)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BHEEM : DBLocationInfoSQLDao : getLocationItem :" + e.getMessage());
        }
        return location;
    }

    private ContentValues setLocationContentValues(Location location) {

        ContentValues values = new ContentValues();
        values.put(DBSQLiteConstants.LOCATION_COUNTRY, location.getCountry());
        values.put(DBSQLiteConstants.LOCATION_STATE, location.getState());
        values.put(DBSQLiteConstants.LOCATION_DISTRICT, location.getDistrict());
        values.put(DBSQLiteConstants.LOCATION_TALUK, location.getTaluk());
        values.put(DBSQLiteConstants.LOCATION_LAT, location.getLatitude());
        values.put(DBSQLiteConstants.LOCATION_LONG, location.getLongitude());
        values.put(DBSQLiteConstants.LOCATION_LANDMARK, location.getLandMark());
        return values;
    }
}
