/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */
package com.example.bheemesh.myapplication.dao;

import com.example.bheemesh.myapplication.beans.BaseItem;

import java.util.ArrayList;

/**
 * Groups related Dao interface
 *
 * @author bheemesh
 */
public interface DBItemInfoDao {


  void open();

  void close();

  ArrayList<BaseItem> getBaseItems(String placeString);

  ArrayList<BaseItem> getAllBaseItems();

  ArrayList<BaseItem> getRecentBaseItems();

  ArrayList<BaseItem> getFavouritesBaseItems();

  void updateNewsItems(String newsVersion, ArrayList<BaseItem> baseItems);

  void updateNews(BaseItem newsObj);
}
