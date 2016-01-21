/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */
package com.example.bheemesh.myapplication.dao;

import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.beans.Location;

import java.util.ArrayList;

/**
 * Groups related Dao interface
 *
 * @author bheemesh
 */
public interface DBLocationInfoDao {


  void open();

  void close();

  ArrayList<Location> getLocationItems(String placeString);

  ArrayList<Location> getAllLocationItems();

  Location getLocation(String locationString);

  String updateLocation(Location locationObj);
}
